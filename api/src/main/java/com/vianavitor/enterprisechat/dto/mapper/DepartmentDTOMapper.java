package com.vianavitor.enterprisechat.dto.mapper;

import com.vianavitor.enterprisechat.dto.department.DepartmentCreateDTO;
import com.vianavitor.enterprisechat.dto.department.DepartmentRespDTO;
import com.vianavitor.enterprisechat.dto.department.DepartmentUpdateDTO;
import com.vianavitor.enterprisechat.model.Department;

public class DepartmentDTOMapper implements CustomMapper<Department> {
    private static final DepartmentDTOMapper instance = new DepartmentDTOMapper();

    private DepartmentDTOMapper() {}

    public static DepartmentDTOMapper getInstance() {
        return instance;
    }

    @Override
    public Department createDTOtoEntity(Record cDto) {
        DepartmentCreateDTO dto = (DepartmentCreateDTO) cDto;

        Department department = new Department();
        department.setName(dto.name());
        department.setDescription(dto.description());

        return department;
    }

    @Override
    public Department updateDTOtoEntity(Record uDto) {
        DepartmentUpdateDTO dto = (DepartmentUpdateDTO) uDto;

        Department department = new Department();
        department.setId(dto.id());
        department.setName(dto.name());
        department.setDescription(dto.description());
        department.setMembersCount(dto.membersCount());

        return department;
    }

    @Override
    public DepartmentRespDTO entityToRespDTO(Department department) {
        return new DepartmentRespDTO(
                department.getName(), department.getDescription(), department.getMembersCount()
        );
    }
}
