package com.vianavitor.enterprisechat.dto.task;

import java.time.LocalDate;

public record TaskCreateDTO(
        String name,
        String description,
        Long creatorId,
        String priority,
        LocalDate expireAt
) {
}
