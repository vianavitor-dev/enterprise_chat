package com.vianavitor.enterprisechat.dao;

import com.vianavitor.enterprisechat.model.Team;

import java.util.List;

public interface TeamDAO extends GenericDao<Team, Long> {
    List<Team> getByName(String name);

    // get teams that belong to the department
    List<Team> getByDepartment(Long departmentId);
}
