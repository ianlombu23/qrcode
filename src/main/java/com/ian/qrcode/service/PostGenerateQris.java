package com.ian.qrcode.service;

import com.google.zxing.WriterException;
import com.ian.qrcode.model.response.PostCreateQrisResponse;
import com.ian.qrcode.util.QrCodeUtil;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class PostGenerateQris {

    private final QrCodeUtil qrCodeUtil;

    public PostGenerateQris(QrCodeUtil qrCodeUtil) {
        this.qrCodeUtil = qrCodeUtil;
    }

    public PostCreateQrisResponse execute() throws IOException, WriterException {
        String PNG = "PNG";
        BufferedImage qrImage = qrCodeUtil.generateQrImage();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(qrImage, PNG, byteArrayOutputStream);
        byte[] qrBytes = byteArrayOutputStream.toByteArray();
        String imageEncode = Base64.getEncoder().encodeToString(qrBytes);
        return PostCreateQrisResponse.builder()
                .extension(PNG)
                .qrisImage(imageEncode)
                .build();
    }

}
