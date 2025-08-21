package com.ian.qrcode.controller;

import com.google.zxing.WriterException;
import com.ian.qrcode.model.response.PostCreateQrisResponse;
import com.ian.qrcode.service.PostGenerateQris;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/qris/v1/")
public class QrisController {

    private final PostGenerateQris postGenerateQris;

    public QrisController(PostGenerateQris postGenerateQris) {
        this.postGenerateQris = postGenerateQris;
    }

    @PostMapping("/create")
    public PostCreateQrisResponse generateQr() throws IOException, WriterException {
        return postGenerateQris.execute();
    }
}
