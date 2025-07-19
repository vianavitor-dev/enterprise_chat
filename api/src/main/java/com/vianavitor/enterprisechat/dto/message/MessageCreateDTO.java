package com.vianavitor.enterprisechat.dto.message;

public record MessageCreateDTO(
        String text,
        Long creatorId
) {
}
