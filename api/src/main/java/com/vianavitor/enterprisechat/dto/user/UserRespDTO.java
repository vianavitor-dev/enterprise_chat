package com.vianavitor.enterprisechat.dto.user;

public record UserRespDTO (
        String name,
        String email,
        boolean admin
) {
}
