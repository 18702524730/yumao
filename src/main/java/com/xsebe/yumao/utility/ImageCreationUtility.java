package com.xsebe.yumao.utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public final class ImageCreationUtility {

    private static void _drawString(Graphics2D g2, Font stringFont, Color stringColor, float x, float y, String s) {
        g2.setFont(stringFont);
        g2.setPaint(stringColor);

        g2.drawString(s, x, y);
    }

    /**
     * 输出声明函图片。
     */
    public static synchronized void statementImageJpgOutput(String no, String stopName, String content, OutputStream output) throws IOException {
        int maxWidth = 27;

        InputStream logoImgInput = ImageCreationUtility.class.getResourceAsStream("/imgcres/statement/logo.png");
        BufferedImage logo = ImageIO.read(logoImgInput);
        logoImgInput.close();
        InputStream caasaImgInput = ImageCreationUtility.class.getResourceAsStream("/imgcres/statement/caasa.png");
        BufferedImage caasa = ImageIO.read(caasaImgInput);
        caasaImgInput.close();

        BufferedImage bi = new BufferedImage(750, 390, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = bi.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, bi.getWidth(), bi.getHeight());

        Font defaultFont = new Font("黑体", Font.PLAIN, 15);
        Color defaultColor = Color.decode("#333333");

        Font titleFont = new Font("黑体", Font.PLAIN, 22);

        Font noFont = new Font("黑体", Font.PLAIN, 13);

        // LOGO图
        g2.drawImage(logo, 28, 28, null);

        // 声明函名称
        _drawString(g2, titleFont, defaultColor, 251.4f, 63.39f, "原创保护公证保管声明函");

        // 声明函编号标题
        _drawString(g2, noFont, defaultColor, 269.4f, 93.39f, "声明函编号:");

        // 声明函编号值
        _drawString(g2, noFont, defaultColor, 351.4f, 93.39f, no);

        // 声明函店铺名
        float[] xStopNameLengthIndex = new float[] {
                180.0f,
                171.5f,
                163.0f,
                150.0f,
                154.5f,
                146.0f,
                137.5f,
                129.0f,
                120.5f,
                112.0f
        };
        float xStopName = 108.0f;
        try {
            xStopName = xStopNameLengthIndex[stopName.length()-1];
        } catch(Throwable ex) {
        }
        _drawString(g2, defaultFont, defaultColor, xStopName, 166.0f, stopName);

        // 声明函店铺名下线
        g2.drawLine(107, 170, 268, 170);
        char[] cs = content.toCharArray();
        int len;
        float xx;
        int heightWeight = 0;
        for (int i = 0; i < cs.length; i+=maxWidth) {
            len = (cs.length-i < maxWidth? cs.length-i : maxWidth);

            if (i == 0) {
                xx = 274.0f;
            } else {
                xx = 78.0f;
            }

            g2.drawString(new String(cs, i, len), xx, 167.0f + heightWeight);
            heightWeight+=30;
        }

        // 特此声明
        _drawString(g2, noFont, defaultColor, 78.0f, 290.0f, "特此声明:");

        // 保护原创 打击侵权
        _drawString(g2, noFont, defaultColor, 78.0f, 320.0f, "保护原创 打击侵权");

        // 签发
        _drawString(g2, noFont, defaultColor, 480.0f, 290.0f, "签发:");

        // CAASA图
        g2.drawImage(caasa, 550, 260, null);

        // 中国反侵权假冒创新战略联盟
        _drawString(g2, noFont, defaultColor, 512.0f, 340.0f, "中国反侵权假冒创新战略联盟");

        ImageIO.write(bi, "jpg", output);
    }
}

