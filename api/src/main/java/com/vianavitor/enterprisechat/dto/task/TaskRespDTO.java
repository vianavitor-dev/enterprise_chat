package com.vianavitor.enterprisechat.dto.task;

import java.time.LocalDate;

public record TaskRespDTO (
        Long id,
        String name,
        String description,
        String state,
        String creatorName,
        String priority,
        LocalDate expireAt
) {
}
