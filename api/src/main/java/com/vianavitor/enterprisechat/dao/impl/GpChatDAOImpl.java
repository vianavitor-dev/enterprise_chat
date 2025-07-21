package com.vianavitor.enterprisechat.dao.impl;

import com.vianavitor.enterprisechat.dao.GpChatDAO;
import com.vianavitor.enterprisechat.model.GroupChat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class GpChatDAOImpl implements GpChatDAO {
    @Autowired
    private EntityManager em;

    @Override
    public List<GroupChat> getByGroupId(Long groupId) {
        TypedQuery<GroupChat> query = em.createQuery(
                "SELECT gc FROM GroupChat gc WHERE gc.group.id = :id",
                GroupChat.class
        );

        query.setParameter("id", groupId);
        return query.getResultList();
    }

    @Override
    public List<GroupChat> getByMessageId(Long messageId) {
        TypedQuery<GroupChat> query = em.createQuery(
                "SELECT gc FROM GroupChat gc WHERE gc.message.id = :id",
                GroupChat.class
        );

        query.setParameter("id", messageId);
        return query.getResultList();
    }

    @Override
    public List<GroupChat> getAll() {
        TypedQuery<GroupChat> query = em.createQuery(
                "SELECT gc FROM GroupChat gc",
                GroupChat.class
        );

        return query.getResultList();
    }

    @Override
    public Optional<GroupChat> getById(Long id) {
        return Optional.ofNullable(em.find(GroupChat.class, id));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void create(GroupChat data) {
        try {
            em.persist(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void modify(GroupChat data) {
        try {
            em.merge(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void remove(GroupChat data) {
        try {
            em.remove(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
