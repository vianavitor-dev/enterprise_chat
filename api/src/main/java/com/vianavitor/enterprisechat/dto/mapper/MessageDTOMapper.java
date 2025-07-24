package com.vianavitor.enterprisechat.dto.mapper;

import com.vianavitor.enterprisechat.dto.message.MessageCreateDTO;
import com.vianavitor.enterprisechat.dto.message.MessageRespDTO;
import com.vianavitor.enterprisechat.dto.message.MessageUpdateDTO;
import com.vianavitor.enterprisechat.dto.user.CreatorDTO;
import com.vianavitor.enterprisechat.model.Message;

public class MessageDTOMapper implements CustomMapper<Message> {
    private static final MessageDTOMapper instance = new MessageDTOMapper();

    private MessageDTOMapper() {}

    public static MessageDTOMapper getInstance() {
        return instance;
    }

    @Override
    public Message createDTOtoEntity(Record cDto) {
        MessageCreateDTO dto = (MessageCreateDTO) cDto;

        Message msg = new Message();
        msg.setText(dto.text());

        return msg;
    }

    @Override
    public Message updateDTOtoEntity(Record uDto) {
        MessageUpdateDTO dto = (MessageUpdateDTO) uDto;

        Message msg = new Message();
        msg.setId(dto.id());
        msg.setText(dto.text());

        return msg;
    }

    @Override
    public MessageRespDTO entityToRespDTO (Message msg) {
        CreatorDTO creatorDto = UserDTOMapper.getInstance()
                .entityToCreatorDTO(msg.getCreator());

        return new MessageRespDTO(
                msg.getId(), msg.getText(),
                creatorDto
        );
    }
}
