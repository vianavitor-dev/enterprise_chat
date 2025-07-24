package com.vianavitor.enterprisechat.dto.mapper;

import com.vianavitor.enterprisechat.dto.group.member.GpMemberCreateDTO;
import com.vianavitor.enterprisechat.dto.group.member.GpMemberRespDTO;
import com.vianavitor.enterprisechat.dto.group.member.GpMemberUpdateDTO;
import com.vianavitor.enterprisechat.dto.user.UserRespDTO;
import com.vianavitor.enterprisechat.model.GroupMember;

public class GpMemberDTOMapper implements CustomMapper<GroupMember> {
    private static final GpMemberDTOMapper instance = new GpMemberDTOMapper();

    private GpMemberDTOMapper() {};

    public static GpMemberDTOMapper getInstance() {
        return instance;
    }

    @Override
    public GroupMember createDTOtoEntity(Record cDto) {
        GpMemberCreateDTO dto = (GpMemberCreateDTO) cDto;

        GroupMember gpMember = new GroupMember();
        gpMember.setActive(true);
        gpMember.setCreatedAt();
        gpMember.setInCharge(dto.isInCharge());

        return gpMember;
    }

    @Override
    public GroupMember updateDTOtoEntity(Record uDto) {
        GpMemberUpdateDTO dto = (GpMemberUpdateDTO) uDto;

        GroupMember gpMember = new GroupMember();
        gpMember.setId(dto.id());
        gpMember.setInCharge(dto.isInCharge());
        gpMember.setActive(dto.active());
        gpMember.setUpdatedAt();

        return gpMember;
    }

    @Override
    public GpMemberRespDTO entityToRespDTO(GroupMember groupMember) {
        UserRespDTO userDto = UserDTOMapper.getInstance()
                .entityToRespDTO(groupMember.getUser());

        return new GpMemberRespDTO(
                groupMember.getGroup().getName(),
                userDto,
                groupMember.getCreatedAt(),
                groupMember.getUpdatedAt(),
                groupMember.isInCharge()
        );
    }
}
