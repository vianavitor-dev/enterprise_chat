package com.vianavitor.enterprisechat.dto.group.chat;

import java.time.LocalDate;

public record GpChatRespDTO (
        String groupName,
        String messageText,
        LocalDate createdAt,
        LocalDate updateAt,
        boolean isExpired
) {
}
