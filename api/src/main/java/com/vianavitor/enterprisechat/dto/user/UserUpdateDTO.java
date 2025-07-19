package com.vianavitor.enterprisechat.dto.user;

public record UserUpdateDTO(
        Long id,
        String name,
        String email,
        String password,
        boolean admin
) {
}
