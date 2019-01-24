package com.ocean;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;

/**
 * Created by Ocean on 2019/1/24 12:43.
 */
public class QRCodeTest {

    public static BufferedImage createImage(String content) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 150, 150,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    @Test
    public void getQRCode(){
        try {
            BufferedImage image = QRCodeTest.createImage("https://mmbdc.maoming.gov.cn/bdc/d?4P-c77");
            BASE64Encoder encoder = new BASE64Encoder();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArray);
            System.out.println(encoder.encode(byteArray.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
