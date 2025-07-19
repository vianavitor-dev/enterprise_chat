package com.vianavitor.enterprisechat.dto.group.chat;

import java.time.LocalDate;

public record GpChatUpdateDTO(
        Long id,
        Long groupId,
        Long messageId,
        LocalDate updateAt,
        boolean active
) {
}
