package com.abhimanyu.reservation;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {
public static BufferedImage generateQRCodeImage(
        String data,
        int width,
        int height
) {

    try {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Map<EncodeHintType, Object> hints = new HashMap<>();

        hints.put(EncodeHintType.MARGIN, 1);

        BitMatrix bitMatrix = qrCodeWriter.encode(
                data,
                BarcodeFormat.QR_CODE,
                width,
                height,
                hints
        );

        BufferedImage image = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB
        );

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                image.setRGB(
                        x,
                        y,
                        bitMatrix.get(x, y)
                                ? Color.BLACK.getRGB()
                                : Color.WHITE.getRGB()
                );

            }

        }

        return image;

    } catch (Exception e) {

        e.printStackTrace();

        return null;

    }

}
    public static ImageIcon generateQRCode(String data, int width, int height) {
        

        try {

            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            Map<EncodeHintType, Object> hints = new HashMap<>();

            hints.put(EncodeHintType.MARGIN, 1);

            BitMatrix bitMatrix = qrCodeWriter.encode(
                    data,
                    BarcodeFormat.QR_CODE,
                    width,
                    height,
                    hints
            );

            BufferedImage image = new BufferedImage(
                    width,
                    height,
                    BufferedImage.TYPE_INT_RGB
            );

            for (int x = 0; x < width; x++) {

                for (int y = 0; y < height; y++) {

                    image.setRGB(
                            x,
                            y,
                            bitMatrix.get(x, y)
                                    ? Color.BLACK.getRGB()
                                    : Color.WHITE.getRGB()
                    );

                }

            }

            return new ImageIcon(image);

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        }

    }

}