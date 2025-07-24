package com.vianavitor.enterprisechat.dao.impl;

import com.vianavitor.enterprisechat.dao.GroupDAO;
import com.vianavitor.enterprisechat.model.Group;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class GroupDAOImpl implements GroupDAO {
    @Autowired
    private EntityManager em;

    @Override
    public List<Group> getByType(String type) {
        TypedQuery<Group> query = em.createQuery(
                "SELECT g FROM Group g WHERE g.type = :type",
                Group.class
        );

        query.setParameter("type", type);
        return query.getResultList();
    }

    @Override
    public List<Group> getByName(String name) {
        TypedQuery<Group> query = em.createQuery(
                "SELECT g FROM Group g WHERE g.name = :name",
                Group.class
        );

        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Group> getAll() {
        TypedQuery<Group> query = em.createQuery(
                "SELECT g FROM Group g",
                Group.class
        );

        return query.getResultList();
    }

    @Override
    public Optional<Group> getById(Long id) {
        return Optional.ofNullable(em.find(Group.class, id));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void create(Group data) {
        try {
            em.persist(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void modify(Group data) {
        try {
            em.merge(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void remove(Group data) {
        try {
            em.remove(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
