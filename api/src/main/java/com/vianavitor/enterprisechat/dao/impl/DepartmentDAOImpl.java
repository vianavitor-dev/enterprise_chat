package com.vianavitor.enterprisechat.dao.impl;

import com.vianavitor.enterprisechat.dao.DepartmentDAO;
import com.vianavitor.enterprisechat.model.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class DepartmentDAOImpl implements DepartmentDAO {
    @Autowired
    private EntityManager em;

    @Override
    public List<Department> getByName(String name) {
        TypedQuery<Department> query = em.createQuery(
                "SELECT d FROM Department WHERE d.name = :name",
                Department.class
        );

        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Department> getAll() {
        TypedQuery<Department> query = em.createQuery(
                "SELECT d FROM Department",
                Department.class
        );

        return query.getResultList();
    }

    @Override
    public Optional<Department> getById(Long id) {
        return Optional.ofNullable(em.find(Department.class, id));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void create(Department data) {
        try {
            em.persist(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void modify(Department data) {
        try {
            em.merge(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void remove(Department data) {
        try {
            em.remove(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
