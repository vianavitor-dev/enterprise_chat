package com.vianavitor.enterprisechat.dao;

import com.vianavitor.enterprisechat.model.Department;

import java.util.List;

public interface DepartmentDAO extends GenericDao<Department, Long> {

    List<Department> getByName(String name);

    // get the departments that have names similar to the name parameter
    // e.g.: "test #1", "test #2", ...
    List<Department> getByDuplicateName(String name);
}
