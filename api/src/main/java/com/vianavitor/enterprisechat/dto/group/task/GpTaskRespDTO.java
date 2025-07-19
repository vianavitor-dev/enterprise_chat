package com.vianavitor.enterprisechat.dto.group.task;

import com.vianavitor.enterprisechat.dto.task.TaskRespDTO;

import java.time.LocalDate;

public record GpTaskRespDTO(
        String groupName,
        TaskRespDTO task,
        LocalDate createdAt,
        LocalDate updatedAt
) {
}
