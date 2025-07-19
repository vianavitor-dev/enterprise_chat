package com.vianavitor.enterprisechat.dao;

import com.vianavitor.enterprisechat.model.Group;

import java.util.List;

public interface GroupDAO extends GenericDao<Group, Long> {

    List<Group> getByType(String type);
    List<Group> getByName(String name);
}
