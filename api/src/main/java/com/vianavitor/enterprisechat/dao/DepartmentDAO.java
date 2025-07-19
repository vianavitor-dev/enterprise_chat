package com.vianavitor.enterprisechat.dao;

import com.vianavitor.enterprisechat.model.Department;

import java.util.List;

public interface DepartmentDAO extends GenericDao<Department, Long> {

    List<Department> getByName(String name);
}
