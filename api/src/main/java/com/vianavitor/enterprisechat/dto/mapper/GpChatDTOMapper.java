package com.vianavitor.enterprisechat.dto.mapper;

import com.vianavitor.enterprisechat.dto.group.chat.GpChatCreateDTO;
import com.vianavitor.enterprisechat.dto.group.chat.GpChatRespDTO;
import com.vianavitor.enterprisechat.dto.group.chat.GpChatUpdateDTO;
import com.vianavitor.enterprisechat.model.GroupChat;

public class GpChatDTOMapper implements CustomMapper<GroupChat> {
    private static final GpChatDTOMapper instance = new GpChatDTOMapper();

    private GpChatDTOMapper() {}

    public static GpChatDTOMapper getInstance() {
        return instance;
    }

    @Override
    public GroupChat createDTOtoEntity(Record cDto) {
        GpChatCreateDTO dto = (GpChatCreateDTO) cDto;

        GroupChat gpChat = new GroupChat();
        gpChat.setActive(true);
        gpChat.setCreatedAt();

        return gpChat;
    }

    @Override
    public GroupChat updateDTOtoEntity(Record uDto) {
        GpChatUpdateDTO dto = (GpChatUpdateDTO) uDto;

        GroupChat gpChat = new GroupChat();
        gpChat.setId(dto.id());
        gpChat.setActive(dto.active());
        gpChat.setUpdatedAt();

        return gpChat;
    }

    @Override
    public GpChatRespDTO entityToRespDTO(GroupChat groupChat) {
        return new GpChatRespDTO(
                groupChat.getGroup().getName(),
                groupChat.getMessage().getText(),
                groupChat.getCreatedAt(),
                groupChat.getUpdatedAt(),
                groupChat.isActive()
        );
    }
}
