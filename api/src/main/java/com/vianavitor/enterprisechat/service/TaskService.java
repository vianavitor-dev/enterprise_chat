package com.vianavitor.enterprisechat.service;

import com.vianavitor.enterprisechat.dao.impl.GpMemberDAOImpl;
import com.vianavitor.enterprisechat.dao.impl.GpTaskDAOImpl;
import com.vianavitor.enterprisechat.dao.impl.TaskDAOImpl;
import com.vianavitor.enterprisechat.dao.impl.UserDAOImpl;
import com.vianavitor.enterprisechat.dto.mapper.TaskDTOMapper;
import com.vianavitor.enterprisechat.dto.task.TaskCreateDTO;
import com.vianavitor.enterprisechat.dto.task.TaskRespDTO;
import com.vianavitor.enterprisechat.dto.task.TaskUpdateDTO;
import com.vianavitor.enterprisechat.exceptions.NotFoundResourceException;
import com.vianavitor.enterprisechat.model.Task;
import com.vianavitor.enterprisechat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskDAOImpl dao;

    @Autowired
    private UserDAOImpl userDao;

    @Autowired
    private GpTaskDAOImpl gpTaskDao;

    private final TaskDTOMapper mapper = TaskDTOMapper.getInstance();

    public void create(TaskCreateDTO data) throws NotFoundResourceException {
        Task task = mapper.createDTOtoEntity(data);

        // get the user who create this task
        User creator = userDao.getById(data.creatorId())
                        .orElseThrow(() -> new NotFoundResourceException("user not found"));

        task.setUser(creator);
        dao.create(task);
    }

    public TaskRespDTO get(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("task id is null");
        }

        Task task = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("user not found"));

        return mapper.entityToRespDTO(task);
    }

    public List<TaskRespDTO> getAll() {
        List<TaskRespDTO> taskList = new ArrayList<>();

        dao.getAll().forEach(task -> {
            taskList.add(mapper.entityToRespDTO(task));
        });

        return taskList;
    }

    public List<TaskRespDTO> getByUser(Long userId) {
        if (userId == null) {
            throw new NullPointerException("user id from the task is null");
        }

        List<TaskRespDTO> taskList = new ArrayList<>();

        dao.getByUserId(userId).forEach(task -> {
            taskList.add(mapper.entityToRespDTO(task));
        });

        return taskList;
    }

    public List<TaskRespDTO> getByName(String name) {
        if (name == null || name.isBlank()) {
            throw new NullPointerException("invalid task name value");
        }

        List<TaskRespDTO> taskList = new ArrayList<>();

        dao.getByName(name).forEach(task -> {
            taskList.add(mapper.entityToRespDTO(task));
        });

        return taskList;
    }

    public void update(TaskUpdateDTO data) {
        dao.modify(mapper.updateDTOtoEntity(data));
    }

    public void delete(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("task id is null");
        }

        Task task = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("task not found"));

        // remove the registers where the task exists in the group-task table
        gpTaskDao.getByGroupId(id).forEach(gpTask -> {
            gpTaskDao.remove(gpTask);
        });

        dao.remove(task);
    }
}
