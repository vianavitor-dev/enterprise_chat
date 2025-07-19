package com.vianavitor.enterprisechat.dto.user;

// DTO for user login operation
public record UserLoginDTO(
        String email, String password
) {
}
