package com.vianavitor.enterprisechat.dto.group.member;

import com.vianavitor.enterprisechat.dto.user.UserRespDTO;

import java.time.LocalDate;

public record GpMemberRespDTO(
        String groupName,
        UserRespDTO member,
        LocalDate createdAt,
        LocalDate updatedAt,
        boolean isIncharge
) {
}
