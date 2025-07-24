package com.vianavitor.enterprisechat.dto.team;

public record TeamUpdateDTO(
        Long id,
        String name,
        Long departmentId,
        int membersCount
) {
}
