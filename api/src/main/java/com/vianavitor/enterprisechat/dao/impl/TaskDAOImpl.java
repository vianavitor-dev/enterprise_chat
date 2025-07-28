package com.vianavitor.enterprisechat.dao.impl;

import com.vianavitor.enterprisechat.dao.TaskDAO;
import com.vianavitor.enterprisechat.model.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskDAOImpl implements TaskDAO {
    @Autowired
    private EntityManager em;

    @Override
    public List<Task> getByName(String name) {
        TypedQuery<Task> query = em.createQuery(
                "SELECT t FROM Task t WHERE t.name = :name",
                Task.class
        );

        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Task> getByUserId(Long userId) {
        TypedQuery<Task> query = em.createQuery(
                "SELECT t FROM Task t WHERE t.id = :id",
                Task.class
        );

        query.setParameter("name", userId);
        return query.getResultList();
    }

    @Override
    public List<Task> getAll() {
        TypedQuery<Task> query = em.createQuery(
                "SELECT t FROM Task t",
                Task.class
        );
        
        return query.getResultList();
    }

    @Override
    public Optional<Task> getById(Long aLong) {
        return Optional.empty();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void create(Task data) {
        try {
            em.persist(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void modify(Task data) {
        try {
            em.merge(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void remove(Task data) {
        try {
            em.remove(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
