package com.ssh1y.paperrec.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author chenweihong
 * @project: ssm_sms
 * @description: 绘制验证码图片
 */
public class CreateVerifyCodeImage {

    private static final int WIDTH = 90;
    private static final int HEIGHT = 35;
    /**
     * 验证码字体大小
     */
    private static final int FONT_SIZE = 20;
    /**
     * 验证码
     */
    private static char[] verifyCode;

    /**
     * @description: 获取验证码图片
     * @param: no
     * @return: java.awt.image.BufferedImage
     */
    public static BufferedImage getVerifyCodeImage() {
        // create a image
        /**
         * 验证码图片
         */
        BufferedImage verifyCodeImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = verifyCodeImage.getGraphics();

        verifyCode = generateCheckCode();
        drawBackground(graphics);
        drawRands(graphics, verifyCode);

        graphics.dispose();

        return verifyCodeImage;
    }

    /**
     * @description: 获取验证码
     * @param: no
     * @return: char[]
     */
    public static char[] getVerifyCode() {
        return verifyCode;
    }

    /**
     * @description: 随机生成验证码
     * @param: no
     * @return: char[]
     */
    private static char[] generateCheckCode() {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] rands = new char[4];
        for (int i = 0; i < 4; i++) {
            int rand = (int) (Math.random() * (10 + 26 * 2));
            rands[i] = chars.charAt(rand);
        }
        return rands;
    }

    /**
     * @description: 绘制验证码
     * @param: g
     * @param: rands
     * @return: void
     */
    private static void drawRands(Graphics g, char[] rands) {
        g.setFont(new Font("Console", Font.BOLD, FONT_SIZE));

        for (int i = 0; i < rands.length; i++) {

            g.setColor(getRandomColor());
            g.drawString("" + rands[i], i * FONT_SIZE + 10, 25);
        }
    }

    /**
     * @description: 绘制验证码图片背景
     * @param: g
     * @return: void
     */
    private static void drawBackground(Graphics g) {

        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 绘制验证码干扰点
        for (int i = 0; i < 200; i++) {
            int x = (int) (Math.random() * WIDTH);
            int y = (int) (Math.random() * HEIGHT);
            g.setColor(getRandomColor());
            g.drawOval(x, y, 1, 1);

        }
    }


    /**
     * @description: 获取随机颜色
     * @param: no
     * @return: java.awt.Color
     */
    private static Color getRandomColor() {
        Random ran = new Random();
        return new Color(ran.nextInt(220), ran.nextInt(220), ran.nextInt(220));
    }
}