package com.vianavitor.enterprisechat.service;

import com.vianavitor.enterprisechat.dao.impl.GpMemberDAOImpl;
import com.vianavitor.enterprisechat.dao.impl.GroupDAOImpl;
import com.vianavitor.enterprisechat.dao.impl.UserDAOImpl;
import com.vianavitor.enterprisechat.dto.group.member.GpMemberCreateDTO;
import com.vianavitor.enterprisechat.dto.group.member.GpMemberRespDTO;
import com.vianavitor.enterprisechat.dto.group.member.GpMemberUpdateDTO;
import com.vianavitor.enterprisechat.dto.mapper.GpMemberDTOMapper;
import com.vianavitor.enterprisechat.exceptions.NotFoundResourceException;
import com.vianavitor.enterprisechat.model.Group;
import com.vianavitor.enterprisechat.model.GroupMember;
import com.vianavitor.enterprisechat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GpMemberService {
    @Autowired
    private GpMemberDAOImpl dao;

    @Autowired
    private UserDAOImpl userDao;

    @Autowired
    private GroupDAOImpl groupDao;

    private final GpMemberDTOMapper mapper = GpMemberDTOMapper.getInstance();

    public void create(GpMemberCreateDTO data) throws NotFoundResourceException {
        User user = userDao.getById(data.userId())
                .orElseThrow(() -> new NotFoundResourceException("user not found"));

        Group group = groupDao.getById(data.userId())
                .orElseThrow(() -> new NotFoundResourceException("group not found"));

        GroupMember member = mapper.createDTOtoEntity(data);
        member.setUser(user);
        member.setGroup(group);

        dao.create(member);

        // add +1 to the group membersCount, so than update it
        group.setMembersCount(group.getMembersCount() + 1);
        groupDao.modify(group);
    }

    // get who's responsible from the group: manager or team leader
    public GpMemberRespDTO getResponsibleFromGroup(Long groupId) throws NotFoundResourceException {
        if (groupId == null) {
            throw new NullPointerException("group id cannot be null");
        }

        GroupMember member = dao.getResponsibleFromGroup(groupId)
                .orElseThrow(() -> new NotFoundResourceException("not found responsible from group"));

        return mapper.entityToRespDTO(member);
    }

    public GpMemberRespDTO get(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("group-member id cannot be null");
        }

        GroupMember member = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("group-member not found"));

        return mapper.entityToRespDTO(member);
    }

    public List<GpMemberRespDTO> getByGroup(Long groupId) {
        if (groupId == null) {
            throw new NullPointerException("group id cannot be null");
        }

        List<GpMemberRespDTO> gpChatList = new ArrayList<>();

        dao.getByGroupId(groupId).forEach(member -> {
            gpChatList.add(mapper.entityToRespDTO(member));
        });

        return gpChatList;
    }

    public List<GpMemberRespDTO> getByUser(Long userId) {
        if (userId == null) {
            throw new NullPointerException("user id from the group member is null");
        }

        List<GpMemberRespDTO> gpChatList = new ArrayList<>();

        dao.getByUserId(userId).forEach(member -> {
            gpChatList.add(mapper.entityToRespDTO(member));
        });

        return gpChatList;
    }

    public List<GpMemberRespDTO> getAll() {
        List<GpMemberRespDTO> gpChatList = new ArrayList<>();

        dao.getAll().forEach(member -> {
            gpChatList.add(mapper.entityToRespDTO(member));
        });

        return gpChatList;
    }

    public void update(GpMemberUpdateDTO data) {
        dao.modify(mapper.updateDTOtoEntity(data));
    }

    public void delete(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("group-member id is null");
        }

        GroupMember member = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("group-member not found"));

        dao.remove(member);
    }

    // instead of delete the register it will just deactivate it
    public void deactivate(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("group-member id is null");
        }

        GroupMember member = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("group-member not found"));

        member.setActive(false);
        member.setUpdatedAt();

        dao.modify(member);
    }
}
