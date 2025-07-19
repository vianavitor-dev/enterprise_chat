package com.vianavitor.enterprisechat.dao;

import com.vianavitor.enterprisechat.model.Message;

import java.util.List;

public interface MessageDAO extends GenericDao<Message, Long> {

    // share the message with other groups
    void share(Long id, List<Long> destinations);

    // get all messages created by the user.id
    List<Message> getByCreatorId(Long id);
}
