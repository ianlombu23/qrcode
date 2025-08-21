package com.ian.qrcode.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class QrCodeUtil {

    public BufferedImage generateQrImage() throws WriterException, IOException {
        
        BufferedImage qrImage = getBufferedQrImage();
        int logoWidth = qrImage.getWidth() / 5;
        int logoHeight = qrImage.getHeight() / 5;
        int x = (qrImage.getWidth() - logoWidth) / 2;
        int y = (qrImage.getHeight() - logoHeight) / 2;
        
        Image scaledLogo = getQrMark(logoWidth, logoHeight);

        Graphics2D graphics = qrImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.fill(new RoundRectangle2D.Float(x, y, logoWidth, logoHeight, 10, 10));
        graphics.drawImage(scaledLogo, x, y, null);
        graphics.setStroke(new BasicStroke(2));
        graphics.setColor(Color.BLACK);
        graphics.draw(new RoundRectangle2D.Float(x, y, logoWidth, logoHeight, 10, 10));
        graphics.dispose();
        
        return qrImage;
    }

    private static Image getQrMark(int logoWidth, int logoHeight) throws IOException {
        String logoPath = "src/main/resources/static/test.png";
        BufferedImage logo = ImageIO.read(new File(logoPath));
        return logo.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);
    }

    private static BufferedImage getBufferedQrImage() throws WriterException {
        int width = 300;
        int height = 300;
        
        Map<EncodeHintType, Object> hints = feedEncodeHintTypeMap();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode("Citra Kemurnian Lombu", BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage qrImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                qrImage.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }
        return qrImage;
    }

    private static Map<EncodeHintType, Object> feedEncodeHintTypeMap() {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        return hints;
    }

    private BufferedImage resizeLogo(BufferedImage logo, int width, int height) {
        Image tmp = logo.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return resized;
    }
}
