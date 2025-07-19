package com.vianavitor.enterprisechat.dto.team;

import com.vianavitor.enterprisechat.dto.department.DepartmentRespDTO;

public record TeamRespDTO(
    String name,
    DepartmentRespDTO department,
    int membersCount
) {
}
