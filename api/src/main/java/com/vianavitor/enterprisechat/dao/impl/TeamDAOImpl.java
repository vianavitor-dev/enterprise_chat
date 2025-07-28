package com.vianavitor.enterprisechat.dao.impl;

import com.vianavitor.enterprisechat.dao.TeamDAO;
import com.vianavitor.enterprisechat.model.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TeamDAOImpl implements TeamDAO {
    @Autowired
    private EntityManager em;

    @Override
    public List<Team> getByName(String name) {
        TypedQuery<Team> query = em.createQuery(
                "SELECT t FROM Team t WHERE t.name = :name",
                Team.class
        );

        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Team> getByDepartment(Long departmentId) {
        TypedQuery<Team> query = em.createQuery(
                "SELECT t FROM Team t WHERE t.department.id = :id",
                Team.class
        );

        query.setParameter("id", departmentId);
        return query.getResultList();
    }

    @Override
    public List<Team> getAll() {
        TypedQuery<Team> query = em.createQuery(
                "SELECT t FROM Team t",
                Team.class
        );

        return query.getResultList();
    }

    @Override
    public Optional<Team> getById(Long id) {
        return Optional.ofNullable(em.find(Team.class, id));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void create(Team data) {
        try {
            em.persist(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void modify(Team data) {
        try {
            em.merge(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void remove(Team data) {
        try {
            em.remove(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
