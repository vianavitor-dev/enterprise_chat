package com.vianavitor.enterprisechat.dto.message;

public record MessageUpdateDTO(
        Long id,
        String text,
        Long creatorId
) {
}
