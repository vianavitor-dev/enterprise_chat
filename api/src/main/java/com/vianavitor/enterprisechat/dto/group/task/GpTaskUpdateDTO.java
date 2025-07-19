package com.vianavitor.enterprisechat.dto.group.task;

public record GpTaskUpdateDTO(
        Long id,
        Long groupId,
        Long taskId,
        boolean active
) {
}
