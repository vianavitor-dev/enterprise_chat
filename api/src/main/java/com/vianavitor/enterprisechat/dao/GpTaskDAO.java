package com.vianavitor.enterprisechat.dao;

import com.vianavitor.enterprisechat.model.GroupTask;

import java.util.List;

public interface GpTaskDAO extends GenericDao<GroupTask, Long> {
    // get all the group tasks in this group
    List<GroupTask> getByGroupId(Long groupId);

    // get all group tasks who have the same task.id
    List<GroupTask> getByTaskId(Long taskId);
}
