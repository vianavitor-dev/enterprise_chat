package com.vianavitor.enterprisechat.dto.mapper;

import com.vianavitor.enterprisechat.dto.group.task.GpTaskCreateDTO;
import com.vianavitor.enterprisechat.dto.group.task.GpTaskRespDTO;
import com.vianavitor.enterprisechat.dto.group.task.GpTaskUpdateDTO;
import com.vianavitor.enterprisechat.dto.task.TaskRespDTO;
import com.vianavitor.enterprisechat.model.GroupTask;

public class GpTaskDTOMapper implements CustomMapper<GroupTask> {
    private static final GpTaskDTOMapper instance = new GpTaskDTOMapper();

    private GpTaskDTOMapper() {}

    public static GpTaskDTOMapper getInstance() {
        return instance;
    }

    @Override
    public GroupTask createDTOtoEntity(Record cDto) {
        GpTaskCreateDTO dto = (GpTaskCreateDTO) cDto;

        GroupTask gpTask = new GroupTask();
        gpTask.setCreatedAt();
        gpTask.setActive(true);

        return gpTask;
    }

    @Override
    public GroupTask updateDTOtoEntity(Record uDto) {
        GpTaskUpdateDTO dto = (GpTaskUpdateDTO) uDto;

        GroupTask gpTask = new GroupTask();
        gpTask.setId(dto.id());
        gpTask.setActive(dto.active());
        gpTask.setUpdatedAt();

        return gpTask;
    }

    @Override
    public GpTaskRespDTO entityToRespDTO(GroupTask groupTask) {
        TaskRespDTO taskDto = TaskDTOMapper.getInstance()
                .entityToRespDTO(groupTask.getTask());

        return new GpTaskRespDTO(
                groupTask.getGroup().getName(),
                taskDto,
                groupTask.getCreatedAt(),
                groupTask.getUpdatedAt()
        );
    }
}
