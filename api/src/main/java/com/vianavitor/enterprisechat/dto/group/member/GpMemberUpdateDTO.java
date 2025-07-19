package com.vianavitor.enterprisechat.dto.group.member;

public record GpMemberUpdateDTO(
        Long id,
        Long groupId,
        Long userId,
        boolean active,
        boolean isInCharge
) {
}
