package com.ssh1y.paperrec.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @author chenweihong
 * @Description: 用于处理用户输入的query
 */
public class QueryUtil {

    /**
     * 读入两个字符数组，计算余弦相似度
     *
     * @param str1 字符数组1
     * @param str2 字符数组2
     * @return 余弦相似度
     */

    private final static Integer THRESHOLD = 5;

    public static double getSimilarity(String[] str1, String[] str2) {
        // 计算余弦相似度
        double similarity = 0;
        // 计算分子
        double numerator = 0;
        for (int i = 0; i < str1.length; i++) {
            for (int j = 0; j < str2.length; j++) {
                if (str1[i].equals(str2[j])) {
                    numerator++;
                }
            }
        }
        // 计算分母
        double denominator = Math.sqrt(str1.length) * Math.sqrt(str2.length);
        // 计算余弦相似度
        similarity = numerator / denominator;
        return similarity;
    }

    /**
     * 传入query，得到分词后的query
     *
     * @param query 用户输入的query
     * @return 分词后的query
     */
    public static String[] processQuery(String query) throws UnsupportedEncodingException {
        String res = LtpCws.getWords(query);
        // 将res转换为json对象
        JSONObject jsonObject = JSON.parseObject(res);
        // 获取json对象中的data中的word数组
        String[] words = jsonObject.getJSONObject("data").getJSONArray("word").toArray(new String[0]);
        // 去除words中的停用词
        words = LtpCws.removeStopWords(words);
        return words;
    }

    /**
     * 传入一个query和ratings表中的所有query，计算query与ratings表中的所有query的余弦相似度，返回最高的前5
     *
     * @param queryArray ratings表中的所有query
     * @param words      分词后的query
     * @return 余弦相似度最高的前5个query
     */
    public static String[] getTopQuerys(String[] queryArray, String[] words) {

        if (words.length == 0 || queryArray.length == 0) {
            return null;
        }

        // 存储余弦相似度
        double[] similarity = new double[queryArray.length];
        // 计算余弦相似度
        for (int i = 0; i < queryArray.length; i++) {
            String[] queryArray2 = queryArray[i].split(",");
            similarity[i] = getSimilarity(words, queryArray2);
        }
        // 对进行similarity从小到大排序
        // 克隆一个数组，防止对原数组进行排序
        double[] temp = similarity.clone();
        Arrays.sort(temp);
        // 从后往前取出前5个不为0的query，若不足THRESHOLD个，则取出所有不为0的query
        String[] topSome = new String[THRESHOLD];
        int count = 0;
        for (int i = temp.length - 1; i >= 0; i--) {
            if (temp[i] != 0) {
//                top5[count] = queryArray[i];
                // 获取temp[i]在similarity中的索引
                int index = 0;
                for (int j = 0; j < similarity.length && count != THRESHOLD; j++) {
                    if (similarity[j] == temp[i]) {
                        index = j;
                        topSome[count] = queryArray[index];
                        count++;
                    }
                }
            }
            if (count == 5) {
                break;
            }
        }
        return topSome;
    }
}
