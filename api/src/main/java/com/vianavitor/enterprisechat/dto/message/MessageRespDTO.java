package com.vianavitor.enterprisechat.dto.message;

import com.vianavitor.enterprisechat.dto.user.CreatorDTO;

public record MessageRespDTO(
        Long id,
        String text,
        CreatorDTO creator
) {
}
