package com.ian.qrcode.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostCreateQrisResponse {
    private String qrisImage;
    private String extension;
}
