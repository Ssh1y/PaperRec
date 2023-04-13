package com.ssh1y.paperrec.utils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author chenweihong
 * date 2023/4/4
 * 利用讯飞的LTP分词WEB API进行分词
 */
public class LtpCws {
    private static final String WEBTTS_URL = "http://ltpapi.xfyun.cn/v1/cws";
    private static final String APPID = "75e07e64";
    private static final String API_KEY = "74d69442ca793ebc0e0dde0b2fcf21ff";
    private static final String TYPE = "dependent";

    /**
     * 定义一个方法，用于获取分词结果，返回格式为数组
     *
     * @param text 传入的文本
     * @return 分词结果
     */
    public static String getWords(String text) throws UnsupportedEncodingException {

        // 封装请求头
        Map<String, String> header = new LinkedHashMap<>();
        String curTime = System.currentTimeMillis() / 1000L + "";
        String param = "{\"type\":\"" + TYPE + "\"}";
        String paramBase64 = new String(Base64.getEncoder().encode(param.getBytes("UTF-8")));
        String checkSum = Md5Encrypt.encrypt(API_KEY + curTime + paramBase64);
        header.put("X-CurTime", curTime);
        header.put("X-Param", paramBase64);
        header.put("X-Appid", APPID);
        header.put("X-CheckSum", checkSum);
        // 发送请求
        String res = HttpUtil.doPost1(WEBTTS_URL, header, "text=" + URLEncoder.encode(text, "utf-8"));
        return res;
    }

    /**
     * 去除停用词
     * @param words 分词结果
     * @return 去除停用词后的分词结果
     */
    public static String[] removeStopWords(String[] words) {
        // 对words进行去重，去除标点符号和空格
        String[] processedWords = Arrays.stream(words)
                .map(word -> word.replaceAll("[\\pP\\s]+", "")).filter(word -> !word.isEmpty())
                .toArray(String[]::new);
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(processedWords));
        words = uniqueWords.toArray(new String[0]);

        InputStream inputStream = LtpCws.class.getResourceAsStream("/static/stopwords.txt");
        Scanner scanner = new Scanner(inputStream);
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }
        String[] stopWords = lines.toArray(new String[0]);

        // 去除停用词
        List<String> list = new ArrayList<>();
        for (String word : words) {
            boolean flag = true;
            for (String stopWord : stopWords) {
                if (word.equals(stopWord)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                list.add(word);
            }
        }
        return list.toArray(new String[0]);
    }
}
