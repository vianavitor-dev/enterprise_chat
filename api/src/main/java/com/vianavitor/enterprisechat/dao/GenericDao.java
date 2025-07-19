package com.vianavitor.enterprisechat.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao <T, Id> {
    List<T> getAll();
    Optional<T> getById(Id id);
    void create(T data);
    void modify(T data);
    void remove(T data);
}
