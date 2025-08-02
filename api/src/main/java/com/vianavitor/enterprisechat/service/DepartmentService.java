package com.vianavitor.enterprisechat.service;

import com.vianavitor.enterprisechat.dao.impl.*;
import com.vianavitor.enterprisechat.dto.department.DepartmentCreateDTO;
import com.vianavitor.enterprisechat.dto.department.DepartmentRespDTO;
import com.vianavitor.enterprisechat.dto.department.DepartmentUpdateDTO;
import com.vianavitor.enterprisechat.dto.mapper.DepartmentDTOMapper;
import com.vianavitor.enterprisechat.dto.mapper.GpChatDTOMapper;
import com.vianavitor.enterprisechat.exceptions.NotFoundResourceException;
import com.vianavitor.enterprisechat.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentDAOImpl dao;

    @Autowired
    private TeamDAOImpl teamDao;

    @Autowired
    private GpChatDAOImpl chatDao;

    @Autowired
    private GpMemberDAOImpl memberDao;

    @Autowired
    private GpTaskDAOImpl gpTaskDao;

    private final DepartmentDTOMapper mapper = DepartmentDTOMapper.getInstance();

    public void create(DepartmentCreateDTO data) {
        Department department = mapper.createDTOtoEntity(data);

        // get how many times this department name was registered
        // e.g.: test (department name) -> "test #1", "test #2" - returns 3 ("test" is included)
        int duplicateNamesCount = dao.getByDuplicateName(data.name()).size();

        // rename the name of the new department if there is another one with the same name
        if (duplicateNamesCount > 0) {
            department.setName(department.getName() + " #" + duplicateNamesCount);
        }

        dao.create(department);
    }

    public DepartmentRespDTO get(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("department id is null");
        }

        Department department = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("department not found"));

        return mapper.entityToRespDTO(department);
    }

    public List<DepartmentRespDTO> getByName(String name) {
        if (name == null || name.isBlank()) {
            throw new NullPointerException("invalid department name");
        }

        List<DepartmentRespDTO> departmentList = new ArrayList<>();

        dao.getByName(name).forEach(department -> {
            departmentList.add(mapper.entityToRespDTO(department));
        });

        return departmentList;
    }

    public List<DepartmentRespDTO> getAll() {
        List<DepartmentRespDTO> departmentList = new ArrayList<>();

        dao.getAll().forEach(department -> {
            departmentList.add(mapper.entityToRespDTO(department));
        });

        return departmentList;
    }

    public void update(DepartmentUpdateDTO data) {
        dao.modify(mapper.updateDTOtoEntity(data));
    }

    public void delete(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("department id is null");
        }

        Department department = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("department not found"));

        // delete the registers in the tables that have relationship with this department
        teamDao.getByDepartment(id).forEach(team -> {
            teamDao.remove(team);
        });

        chatDao.getByGroupId(id).forEach(chat -> {
            chatDao.remove(chat);
        });

        memberDao.getByGroupId(id).forEach(member -> {
            memberDao.remove(member);
        });

        gpTaskDao.getByGroupId(id).forEach(gpTask -> {
            gpTaskDao.remove(gpTask);
        });

        dao.remove(department);
    }
}
