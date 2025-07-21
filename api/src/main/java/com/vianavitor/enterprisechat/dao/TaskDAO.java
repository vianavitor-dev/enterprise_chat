package com.vianavitor.enterprisechat.dao;

import com.vianavitor.enterprisechat.model.Task;
import java.util.List;

public interface TaskDAO extends
        GenericDao<Task, Long> {

    // - Service Layer: share the current task with the other groups
    // void share(Long id, List<Long> destinations);

    List<Task> getByName(String name);

    // get all the tasks that belongs to determined user
    List<Task> getByUserId(Long userId);
}
