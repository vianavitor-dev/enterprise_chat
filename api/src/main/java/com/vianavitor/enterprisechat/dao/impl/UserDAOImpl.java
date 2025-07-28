package com.vianavitor.enterprisechat.dao.impl;

import com.vianavitor.enterprisechat.dao.UserDAO;
import com.vianavitor.enterprisechat.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private EntityManager em;

    @Override
    public Optional<User> getByEmail(String email) {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.email = ?",
                User.class
        );

        query.setParameter(0, email);

        // it avoids getting exceptions, such as using `getSingleResult()`
        List<User> matches = query.getResultList();

        return matches.stream().findFirst();
    }

    @Override
    public List<User> getAll() {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u",
                User.class
        );

        return query.getResultList();
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void create(User data) {
        try {
            em.persist(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void modify(User data) {
        try {
            em.merge(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void remove(User data) {
        try {
            em.remove(data);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
