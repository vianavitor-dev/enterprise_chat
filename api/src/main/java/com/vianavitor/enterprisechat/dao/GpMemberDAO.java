package com.vianavitor.enterprisechat.dao;

import com.vianavitor.enterprisechat.model.GroupMember;

import java.util.List;
import java.util.Optional;

public interface GpMemberDAO extends GenericDao<GroupMember, Long> {
    // get all the members in this group
    List<GroupMember> getByGroupId(Long groupId);

    // get all members who have the same user.id
    List<GroupMember> getByUserId(Long userId);

    // get who's in charge of the group
    Optional<GroupMember> getResponsibleFromGroup(Long groupId);
}
