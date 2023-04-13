package com.ssh1y.paperrec;

import com.ssh1y.paperrec.utils.LatentFactorModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author chenweihong
 */
public class TestForLFM {
    public static void main(String[] args) {
        List<Double[]> data = readRatingsFile("/Users/chenweihong/Desktop/JAVAproject/PaperRec/src/main/resources/static/ratings.csv");
        // 将data变成double[][]数组
        double[][] dataset = new double[data.size()][3];
        for (int i = 0; i < data.size(); i++) {
            dataset[i][0] = data.get(i)[0];
            dataset[i][1] = data.get(i)[1];
            dataset[i][2] = data.get(i)[2];
        }
        System.out.println(Arrays.toString(dataset[0]));
        LatentFactorModel model = new LatentFactorModel(dataset, 0.02, 0.01, 0.01, 10, 100);
        model.fit();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入用户ID：");
            int user = scanner.nextInt();
            System.out.println("请输入电影ID：");
            int movie = scanner.nextInt();
            double predict = model.predict(user, movie);
            System.out.println(predict);
        }
    }

    public static List<Double[]> readRatingsFile(String filename) {
        List<Double[]> data = new ArrayList<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                Double[] row = new Double[3];
                row[0] = Double.parseDouble(parts[0]);
                row[1] = Double.parseDouble(parts[1]);
                row[2] = Double.parseDouble(parts[2]);
                data.add(row);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}
