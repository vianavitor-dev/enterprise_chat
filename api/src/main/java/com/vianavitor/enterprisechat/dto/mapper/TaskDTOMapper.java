package com.vianavitor.enterprisechat.dto.mapper;

import com.vianavitor.enterprisechat.dto.task.TaskCreateDTO;
import com.vianavitor.enterprisechat.dto.task.TaskRespDTO;
import com.vianavitor.enterprisechat.dto.task.TaskUpdateDTO;
import com.vianavitor.enterprisechat.model.Task;

public class TaskDTOMapper implements CustomMapper<Task> {
    private static final TaskDTOMapper instance = new TaskDTOMapper();

    private TaskDTOMapper() {}

    public static TaskDTOMapper getInstance() {
        return instance;
    }

    @Override
    public Task createDTOtoEntity(Record cDto) {
        TaskCreateDTO dto = (TaskCreateDTO) cDto;

        Task task = new Task();
        task.setName(dto.name());
        task.setDescription(dto.description());
        task.setPriority(dto.priority());
        task.setExpireAt(dto.expireAt());

        return task;
    }

    @Override
    public Task updateDTOtoEntity(Record uDto) {
        TaskUpdateDTO dto = (TaskUpdateDTO) uDto;

        Task task = new Task();
        task.setId(dto.id());
        task.setName(dto.name());
        task.setDescription(dto.description());
        task.setState(dto.state());
        task.setPriority(dto.priority());
        task.setExpireAt(dto.expireAt());

        return task;
    }

    @Override
    public TaskRespDTO entityToRespDTO(Task entity) {
        return new TaskRespDTO(
                entity.getId(), entity.getName(), entity.getDescription(),
                entity.getState(), entity.getUser().getName(), entity.getPriority(),
                entity.getExpireAt()
        );
    }

}
