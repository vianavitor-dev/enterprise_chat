package com.vianavitor.enterprisechat.dto.user;

public record UserCreateDTO(
        String name,
        String email,
        String password,
        boolean admin
) {
}
