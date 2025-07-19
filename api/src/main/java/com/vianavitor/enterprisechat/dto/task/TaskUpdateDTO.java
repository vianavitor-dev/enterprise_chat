package com.vianavitor.enterprisechat.dto.task;

import java.time.LocalDate;

public record TaskUpdateDTO(
        Long id,
        String name,
        String description,
        String state,
        String priority,
        LocalDate expireAt
) {
}
