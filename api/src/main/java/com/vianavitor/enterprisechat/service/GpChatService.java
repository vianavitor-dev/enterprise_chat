package com.vianavitor.enterprisechat.service;

import com.vianavitor.enterprisechat.dao.impl.GpChatDAOImpl;
import com.vianavitor.enterprisechat.dao.impl.GroupDAOImpl;
import com.vianavitor.enterprisechat.dao.impl.MessageDAOImpl;
import com.vianavitor.enterprisechat.dto.ShareableDTO;
import com.vianavitor.enterprisechat.dto.group.chat.GpChatCreateDTO;
import com.vianavitor.enterprisechat.dto.group.chat.GpChatRespDTO;
import com.vianavitor.enterprisechat.dto.group.chat.GpChatUpdateDTO;
import com.vianavitor.enterprisechat.dto.mapper.GpChatDTOMapper;
import com.vianavitor.enterprisechat.exceptions.NotFoundResourceException;
import com.vianavitor.enterprisechat.model.Group;
import com.vianavitor.enterprisechat.model.GroupChat;
import com.vianavitor.enterprisechat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GpChatService {
    @Autowired
    private GpChatDAOImpl dao;

    @Autowired
    private MessageDAOImpl messageDao;

    @Autowired
    private GroupDAOImpl groupDao;

    private final GpChatDTOMapper mapper = GpChatDTOMapper.getInstance();

    public void share(ShareableDTO data) throws NotFoundResourceException {
        if (data.id() == null) {
            throw new NullPointerException("message id is null");
        }

        Message message = messageDao.getById(data.id())
                .orElseThrow(() -> new NotFoundResourceException("message not found"));

        data.destinations().forEach(groupId -> {
            Group group = groupDao.getById(groupId)
                    .orElseThrow(() -> new NotFoundResourceException("group not found"));

            GroupChat chat = mapper.createDTOtoEntity(data);
            chat.setMessage(message);
            chat.setGroup(group);

            dao.create(chat);
        });
    }

    public void create(GpChatCreateDTO data) throws NotFoundResourceException {
        Message message = messageDao.getById(data.messageId())
                .orElseThrow(() -> new NotFoundResourceException("message not found"));

        Group group = groupDao.getById(data.groupId())
                .orElseThrow(() -> new NotFoundResourceException("group not found"));

        GroupChat chat = mapper.createDTOtoEntity(data);
        chat.setMessage(message);
        chat.setGroup(group);

        dao.create(chat);
    }

    public GpChatRespDTO get(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("group-chat id is null");
        }

        GroupChat chat = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("group-chat not found"));

        return mapper.entityToRespDTO(chat);
    }

    public List<GpChatRespDTO> getByGroupId(Long groupId) {
        if (groupId == null) {
            throw new NullPointerException("group id from group-chat is null");
        }

        List<GpChatRespDTO> chatList = new ArrayList<>();

        dao.getByGroupId(groupId).forEach(gpChat -> {
            chatList.add(mapper.entityToRespDTO(gpChat));
        });

        return chatList;
    }

    public List<GpChatRespDTO> getByMessage(Long messageId)  {
        if (messageId == null) {
            throw new NullPointerException("message id from group-chat is null");
        }

        List<GpChatRespDTO> chatList = new ArrayList<>();

        dao.getByMessageId(messageId).forEach(gpChat -> {
            chatList.add(mapper.entityToRespDTO(gpChat));
        });

        return chatList;
    }

    public List<GpChatRespDTO> getAll() {
        List<GpChatRespDTO> chatList = new ArrayList<>();

        dao.getAll().forEach(gpChat -> {
            chatList.add(mapper.entityToRespDTO(gpChat));
        });

        return chatList;
    }

    public void update(GpChatUpdateDTO data) {
        dao.modify(mapper.updateDTOtoEntity(data));
    }

    public void delete(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("group-chat id is null");
        }

        GroupChat chat = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("group-chat not found"));

        dao.remove(chat);
    }

    public void deactivate(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("group-chat id is null");
        }

        GroupChat chat = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("group-chat not found"));

        chat.setActive(false);
        chat.setUpdatedAt();

        dao.modify(chat);
    }
}



