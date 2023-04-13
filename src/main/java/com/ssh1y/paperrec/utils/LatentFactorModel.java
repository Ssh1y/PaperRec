package com.ssh1y.paperrec.utils;

import java.util.*;

/**
 * @author chenweihong
 * @description Latent Factor Model
 * @createDate 2023-04-08 16：24：00
 */
public class LatentFactorModel {
    private final double[][] dataset;
    private Map<Double, List<Double>> userRatings;
    private Map<Double, List<Double>> itemRatings;
    private double globalMean;
    private Map<Double, double[]> P;
    private Map<Double, double[]> Q;
    private final double alpha;
    private final double regP;
    private final double regQ;
    private final int numberLatentFactors;
    private final int numberEpochs;

    /**
     * 构造函数
     *
     * @param dataset             数据集
     * @param alpha               学习率
     * @param regP                正则化参数
     * @param regQ                正则化参数
     * @param numberLatentFactors 隐因子个数
     * @param numberEpochs        迭代次数
     */
    public LatentFactorModel(double[][] dataset, double alpha, double regP, double regQ, int numberLatentFactors, int numberEpochs) {
        this.dataset = dataset;
        this.alpha = alpha;
        this.regP = regP;
        this.regQ = regQ;
        this.numberLatentFactors = numberLatentFactors;
        this.numberEpochs = numberEpochs;
    }

    /**
     * 训练模型
     */
    public void fit() {
        this.userRatings = groupRatings(dataset, 0, 1, 2);
        this.itemRatings = groupRatings(dataset, 1, 0, 2);
        this.globalMean = calculateGlobalMean(dataset, 2);

        sgd();
    }

    private double calculateGlobalMean(double[][] dataset, int selectIndex) {
        double sum = 0;
        for (double[] data : dataset) {
            sum += data[selectIndex];
        }
        return sum / dataset.length;
    }

    private Map<Double, List<Double>> groupRatings(double[][] dataset, int groupByIndex, int selectIndex1, int selectIndex2) {
        Map<Double, List<Double>> result = new HashMap<>();
        for (double[] data : dataset) {
            double key = data[groupByIndex];
            double select1 = data[selectIndex1];
            double select2 = data[selectIndex2];
            result.computeIfAbsent(key, k -> new ArrayList<>());
            result.get(key).add(select1);
            result.get(key).add(select2);
        }
        return result;
    }

    private Map<Integer, Map<Double, double[]>> initMatrix() {
        Map<Double, double[]> P = new HashMap<>();
        Map<Double, double[]> Q = new HashMap<>();

        Iterator<Double> iterator = userRatings.keySet().iterator();
        // User-LF P
        for (int i = 0; i < userRatings.size() && iterator.hasNext(); i++) {
            double[] p = new double[numberLatentFactors];
            for (int j = 0; j < numberLatentFactors; j++) {
                p[j] = Math.random();
            }
            P.put(iterator.next(), p);

        }

        iterator = itemRatings.keySet().iterator();
        // Item-LF Q
        for (int i = 0; i < itemRatings.size() && iterator.hasNext(); i++) {
            double[] q = new double[numberLatentFactors];
            for (int j = 0; j < numberLatentFactors; j++) {
                q[j] = Math.random();
            }
            Q.put(iterator.next(), q);
        }
        Map<Integer, Map<Double, double[]>> result = new HashMap<>();
        result.put(0, P);
        result.put(1, Q);
        return result;
    }


    /**
     * 随机梯度下降
     */
    private void sgd() {

        Map<Integer, Map<Double, double[]>> matrix = initMatrix();
        this.P = matrix.get(0);
        this.Q = matrix.get(1);

        for (int epoch = 0; epoch < numberEpochs; epoch++) {
            System.out.println("iter" + epoch);
            List<Double> errorList = new ArrayList<>();
            for (double[] data : dataset) {
                double userId = data[0];
                double itemId = data[1];
                double rating = data[2];

                double[] pU = P.get(userId);
                double[] qI = Q.get(itemId);
                double err = rating - dotProduct(pU, qI);


                for (int k = 0; k < numberLatentFactors; k++) {
                    pU[k] += alpha * (err * qI[k] - regP * pU[k]);
                    qI[k] += alpha * (err * pU[k] - regQ * qI[k]);
                }

                P.put(userId, pU);
                Q.put(itemId, qI);

                errorList.add(Math.pow(err, 2));
            }
            System.out.println(Math.sqrt(errorList.stream().mapToDouble(Double::doubleValue).average().getAsDouble()));
        }
    }

    /**
     * 向量点乘
     *
     * @param p 向量p
     * @param q 向量q
     * @return 点乘结果
     */
    private double dotProduct(double[] p, double[] q) {
        double result = 0;
        for (int i = 0; i < p.length; i++) {
            result += p[i] * q[i];
        }
        return result;
    }

    /**
     * 预测
     *
     * @param userId 用户id
     * @param itemId 物品id
     * @return 预测评分
     */
    public double predict(double userId, double itemId) {
        double[] pU = P.get(userId);
        double[] qI = Q.get(itemId);
        return dotProduct(pU, qI);
    }

    public List<Double> getPredictResult(double userId, List<Double> itemIds) {
        List<Double> resultList = new ArrayList<>();
        Map<Double, Double> scoreMap = new HashMap<>();
        for (double itemId : itemIds) {
            double[] pU = P.get(userId);
            double[] qI = Q.get(itemId);
            double score = dotProduct(pU, qI);
            resultList.add(score);
            // 存储itemId和预测评分的对应关系
            scoreMap.put(itemId, score);
        }
        // 按照预测评分从大到小排序
        List<Map.Entry<Double, Double>> sortedList = new ArrayList<>(scoreMap.entrySet());
        sortedList.sort(Map.Entry.<Double, Double>comparingByValue().reversed());
        // 返回排序后的前20个itemIds
        List<Double> result = new ArrayList<>();
        for (Map.Entry<Double, Double> entry : sortedList.subList(0, Math.min(20, sortedList.size()))) {
            result.add(entry.getKey());
        }
        return result;
    }
}
