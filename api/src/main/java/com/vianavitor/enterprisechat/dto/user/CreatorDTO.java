package com.vianavitor.enterprisechat.dto.user;

// DTO designed to be used as a creator of the messages
public record CreatorDTO(
        Long id,
        String name,
        boolean admin
) {
}
