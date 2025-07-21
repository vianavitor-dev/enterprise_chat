package com.vianavitor.enterprisechat.dao.impl;

import com.vianavitor.enterprisechat.dao.GpTaskDAO;
import com.vianavitor.enterprisechat.model.GroupTask;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class GpTaskDAOImpl implements GpTaskDAO {
    @Autowired
    private EntityManager em;

    @Override
    public List<GroupTask> getByGroupId(Long groupId) {
        TypedQuery<GroupTask> query = em.createQuery(
                "SELECT gt FROM GroupTask gt WHERE gt.group.id = :id",
                GroupTask.class
        );

        query.setParameter("id", groupId);
        return query.getResultList();
    }

    @Override
    public List<GroupTask> getByTaskId(Long taskId) {
        TypedQuery<GroupTask> query = em.createQuery(
                "SELECT gt FROM GroupTask gt WHERE gt.task.id = :id",
                GroupTask.class
        );

        query.setParameter("id", taskId);
        return query.getResultList();
    }

    @Override
    public List<GroupTask> getAll() {
        TypedQuery<GroupTask> query = em.createQuery(
                "SELECT gt FROM GroupTask gt",
                GroupTask.class
        );

        return query.getResultList();
    }

    @Override
    public Optional<GroupTask> getById(Long id) {
        return Optional.ofNullable(em.find(GroupTask.class, id));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void create(GroupTask data) {
        try {
            em.persist(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void modify(GroupTask data) {
        try {
            em.merge(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void remove(GroupTask data) {
        try {
            em.remove(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
