package com.vianavitor.enterprisechat.service;

import com.vianavitor.enterprisechat.dao.impl.*;
import com.vianavitor.enterprisechat.dto.mapper.TeamDTOMapper;
import com.vianavitor.enterprisechat.dto.team.TeamCreateDTO;
import com.vianavitor.enterprisechat.dto.team.TeamRespDTO;
import com.vianavitor.enterprisechat.dto.team.TeamUpdateDTO;
import com.vianavitor.enterprisechat.exceptions.NotFoundResourceException;
import com.vianavitor.enterprisechat.model.Department;
import com.vianavitor.enterprisechat.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamDAOImpl dao;

    @Autowired
    private DepartmentDAOImpl departmentDao;

    @Autowired
    private GpChatDAOImpl chatDao;

    @Autowired
    private GpMemberDAOImpl memberDao;

    @Autowired
    private GpTaskDAOImpl gpTaskDao;

    private final TeamDTOMapper mapper = TeamDTOMapper.getInstance();

    public void create(TeamCreateDTO data) throws NotFoundResourceException {
        // get the department which this team belongs
        Department department = departmentDao.getById(data.departmentId())
                .orElseThrow(() -> new NotFoundResourceException("department not found"));

        Team team = mapper.createDTOtoEntity(data);
        team.setDepartment(department);

        dao.create(team);
    }

    public TeamRespDTO get(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("team id is null");
        }

        Team team = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("team not found"));

        return mapper.entityToRespDTO(team);
    }

    public List<TeamRespDTO> getByName(String name) {
        if (name == null || name.isBlank()) {
            throw new NullPointerException("invalid team name");
        }

        List<TeamRespDTO> teamList = new ArrayList<>();

        dao.getByName(name).forEach(team -> {
            teamList.add(mapper.entityToRespDTO(team));
        });

        return teamList;
    }

    public List<TeamRespDTO> getByDepartment(Long departmentId) {
        if (departmentId == null) {
            throw new NullPointerException("department id is null");
        }

        List<TeamRespDTO> teamList = new ArrayList<>();

        dao.getByDepartment(departmentId).forEach(team -> {
            teamList.add(mapper.entityToRespDTO(team));
        });

        return teamList;
    }

    public void update(TeamUpdateDTO data) {
        dao.modify(mapper.updateDTOtoEntity(data));
    }

    public void delete(Long id) throws NotFoundResourceException {
        if (id == null) {
            throw new NullPointerException("team id is null");
        }

        Team team = dao.getById(id)
                .orElseThrow(() -> new NotFoundResourceException("team not found"));

        chatDao.getByGroupId(id).forEach(chat -> {
            chatDao.remove(chat);
        });

        memberDao.getByGroupId(id).forEach(member -> {
            memberDao.remove(member);
        });

        gpTaskDao.getByGroupId(id).forEach(gpTask -> {
            gpTaskDao.remove(gpTask);
        });

        dao.remove(team);
    }
}
