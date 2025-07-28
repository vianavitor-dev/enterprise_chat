package com.vianavitor.enterprisechat.dao.impl;

import com.vianavitor.enterprisechat.dao.GpMemberDAO;
import com.vianavitor.enterprisechat.model.GroupMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GpMemberDAOImpl implements GpMemberDAO {
    @Autowired
    private EntityManager em;

    @Override
    public List<GroupMember> getByGroupId(Long groupId) {
        TypedQuery<GroupMember> query = em.createQuery(
                "SELECT gm FROM GroupMember gm WHERE gm.group.id = :id",
                GroupMember.class
        );

        query.setParameter("id", groupId);
        return query.getResultList();
    }

    @Override
    public List<GroupMember> getByUserId(Long userId) {
        TypedQuery<GroupMember> query = em.createQuery(
                "SELECT gm FROM GroupMember gm WHERE gm.user.id = :id",
                GroupMember.class
        );

        query.setParameter("id", userId);
        return query.getResultList();
    }

    @Override
    public Optional<GroupMember> getResponsibleFromGroup(Long groupId) {
        TypedQuery<GroupMember> query = em.createQuery(
                "SELECT gm FROM GroupMember gm WHERE gm.group.id = :id AND gm.is_in_charge = 1",
                GroupMember.class
        );

        query.setParameter("id", groupId);
        List<GroupMember> matches = query.getResultList();

        return matches.stream().findFirst();
    }

    @Override
    public List<GroupMember> getAll() {
        TypedQuery<GroupMember> query = em.createQuery(
                "SELECT gm FROM GroupMember gm",
                GroupMember.class
        );

        return query.getResultList();
    }

    @Override
    public Optional<GroupMember> getById(Long id) {
        return Optional.ofNullable(em.find(GroupMember.class, id));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void create(GroupMember data) {
        try {
            em.persist(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void modify(GroupMember data) {
        try {
            em.merge(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void remove(GroupMember data) {
        try {
            em.remove(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
