package com.vianavitor.enterprisechat.dao.impl;

import com.vianavitor.enterprisechat.dao.MessageDAO;
import com.vianavitor.enterprisechat.model.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MessageDAOImpl implements MessageDAO {
    @Autowired
    private EntityManager em;

    @Override
    public List<Message> getByCreatorId(Long id) {
        TypedQuery<Message> query = em.createQuery(
                "SELECT m FROM Message m WHERE m.creator.id = :id",
                Message.class
        );

        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<Message> getAll() {
        TypedQuery<Message> query = em.createQuery(
                "SELECT m FROM m Message",
                Message.class
        );

        return query.getResultList();
    }

    @Override
    public Optional<Message> getById(Long id) {
        return Optional.ofNullable(em.find(Message.class, id));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void create(Message data) {
        try {
            em.persist(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void modify(Message data) {
        try {
            em.merge(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void remove(Message data) {
        try {
            em.remove(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
