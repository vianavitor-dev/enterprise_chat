package com.vianavitor.enterprisechat.dao;

import com.vianavitor.enterprisechat.model.GroupChat;

import java.util.List;

public interface GpChatDAO extends GenericDao<GroupChat, Long> {
    // get all the messages in this group
    List<GroupChat> getByGroupId(Long groupId);

    // get all group messages who have the same message.id
    List<GroupChat> getByMessageId(Long messageId);
}
