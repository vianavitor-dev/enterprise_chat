package com.vianavitor.enterprisechat.dto.group.member;

public record GpMemberCreateDTO(
        Long groupId,
        Long userId,
        boolean isInCharge
) {
}
