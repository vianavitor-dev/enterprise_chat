package com.vianavitor.enterprisechat.service;

import com.vianavitor.enterprisechat.dao.impl.GpChatDAOImpl;
import com.vianavitor.enterprisechat.dao.impl.MessageDAOImpl;
import com.vianavitor.enterprisechat.dao.impl.UserDAOImpl;
import com.vianavitor.enterprisechat.dto.mapper.MessageDTOMapper;
import com.vianavitor.enterprisechat.dto.message.MessageCreateDTO;
import com.vianavitor.enterprisechat.dto.message.MessageRespDTO;
import com.vianavitor.enterprisechat.dto.message.MessageUpdateDTO;
import com.vianavitor.enterprisechat.exceptions.NotFoundResourceException;
import com.vianavitor.enterprisechat.model.Message;
import com.vianavitor.enterprisechat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageDAOImpl dao;

    @Autowired
    private UserDAOImpl userDao;

    @Autowired
    private GpChatDAOImpl chatDao;

    @Autowired
    private final MessageDTOMapper mapper = MessageDTOMapper.getInstance();

    public void create(MessageCreateDTO data) throws NotFoundResourceException {
        Message message = mapper.createDTOtoEntity(data);

        User creator = userDao.getById(data.creatorId())
                .orElseThrow(() -> new NotFoundResourceException("user not found"));

        message.setCreator(creator);
        dao.create(message);
    }

    public MessageRespDTO get(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("message id is null");
        }

        Message message = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("message not found"));

        return mapper.entityToRespDTO(message);
    }

    public List<MessageRespDTO> getAll() {
        List<MessageRespDTO> messageList = new ArrayList<>();

        dao.getAll().forEach(message -> {
            messageList.add(mapper.entityToRespDTO(message));
        });

        return messageList;
    }

    public List<MessageRespDTO> getByCreator(Long userId) throws NotFoundResourceException {
        if (userId == null) {
            throw new NullPointerException("user id from the message is null");
        }

        if (userDao.getById(userId).isEmpty()) {
            throw new NotFoundResourceException("user not found");
        }

        List<MessageRespDTO> messageList = new ArrayList<>();

        dao.getByCreatorId(userId).forEach(message -> {
            messageList.add(mapper.entityToRespDTO(message));
        });

        return messageList;
    }

    public void update(MessageUpdateDTO data) {
        dao.modify(mapper.updateDTOtoEntity(data));
    }

    public void delete(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("message id is null");
        }

        Message message = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("message not found"));

        // remove the registers in the group-chat table where this message exists
        chatDao.getByMessageId(id).forEach(chat -> {
            chatDao.remove(chat);
        });

        dao.remove(message);
    }
}
