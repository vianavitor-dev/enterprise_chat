package com.vianavitor.enterprisechat.service;

import com.vianavitor.enterprisechat.dao.impl.GpTaskDAOImpl;
import com.vianavitor.enterprisechat.dao.impl.GroupDAOImpl;
import com.vianavitor.enterprisechat.dao.impl.TaskDAOImpl;
import com.vianavitor.enterprisechat.dto.ShareableDTO;
import com.vianavitor.enterprisechat.dto.group.task.GpTaskCreateDTO;
import com.vianavitor.enterprisechat.dto.group.task.GpTaskRespDTO;
import com.vianavitor.enterprisechat.dto.group.task.GpTaskUpdateDTO;
import com.vianavitor.enterprisechat.dto.mapper.GpTaskDTOMapper;
import com.vianavitor.enterprisechat.exceptions.NotFoundResourceException;
import com.vianavitor.enterprisechat.model.Group;
import com.vianavitor.enterprisechat.model.GroupTask;
import com.vianavitor.enterprisechat.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GpTaskService {
    @Autowired
    private GpTaskDAOImpl dao;

    @Autowired
    private GroupDAOImpl groupDao;

    @Autowired
    private TaskDAOImpl taskDao;

    private final GpTaskDTOMapper mapper = GpTaskDTOMapper.getInstance();

    // share a task by its id to all the destination groups
    public void share(ShareableDTO data) throws NotFoundResourceException {
        if (data.id() == null) {
            throw new NullPointerException("task id is null");
        }

        Task task = taskDao.getById(data.id())
                .orElseThrow(() -> new NotFoundResourceException("task not found"));

        data.destinations().forEach(groupId -> {
            Group group = groupDao.getById(groupId)
                    .orElseThrow(() -> new NotFoundResourceException("group not found"));

            GroupTask gpTask = mapper.createDTOtoEntity(data);
            gpTask.setTask(task);
            gpTask.setGroup(group);

            dao.create(gpTask);
        });

    }

    public void create(GpTaskCreateDTO data) throws NotFoundResourceException {
        Task task = taskDao.getById(data.taskId())
                .orElseThrow(() -> new NotFoundResourceException("task not found"));

        Group group = groupDao.getById(data.groupId())
                .orElseThrow(() -> new NotFoundResourceException("group not found"));

        GroupTask gpTask = mapper.createDTOtoEntity(data);
        gpTask.setTask(task);
        gpTask.setGroup(group);

        dao.create(gpTask);
    }

    public GpTaskRespDTO get(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("group-task id is null");
        }

        GroupTask gpTask = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("group-task not found"));

        return mapper.entityToRespDTO(gpTask);
    }

    public List<GpTaskRespDTO> getByGroup(Long groupId) throws NotFoundResourceException {
        if (groupId == null) {
            throw new NullPointerException("group id cannot be null");
        }

        List<GpTaskRespDTO> gpTaskList = new ArrayList<>();

        dao.getByGroupId(groupId).forEach(gpTask -> {
            gpTaskList.add(mapper.entityToRespDTO(gpTask));
        });

        return gpTaskList;
    }

    public List<GpTaskRespDTO> getByTask(Long taskId) throws NotFoundResourceException {
        if (taskId == null) {
            throw new NullPointerException("task id cannot be null");
        }

        List<GpTaskRespDTO> gpTaskList = new ArrayList<>();

        dao.getByTaskId(taskId).forEach(gpTask -> {
            gpTaskList.add(mapper.entityToRespDTO(gpTask));
        });

        return gpTaskList;
    }

    public List<GpTaskRespDTO> getAll() throws NotFoundResourceException {
        List<GpTaskRespDTO> gpTaskList = new ArrayList<>();

        dao.getAll().forEach(gpTask -> {
            gpTaskList.add(mapper.entityToRespDTO(gpTask));
        });

        return gpTaskList;
    }

    public void update(GpTaskUpdateDTO data) {
        dao.modify(mapper.updateDTOtoEntity(data));
    }

    public void delete(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("group-task id is null");
        }

        GroupTask gpTask = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("grop-task not found"));

        dao.remove(gpTask);
    }

    public void deactivate(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("group-task id is null");
        }

        GroupTask gpTask = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("grop-task not found"));

        gpTask.setActive(false);
        gpTask.setUpdatedAt();

        dao.modify(gpTask);
    }
}
