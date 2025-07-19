package com.vianavitor.enterprisechat.dao;

import com.vianavitor.enterprisechat.model.User;
import java.util.Optional;

public interface UserDAO extends GenericDao<User, Long> {

    Optional<User> getByEmail(String email);
}
