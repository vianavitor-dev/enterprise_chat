package com.vianavitor.enterprisechat.dto.mapper;

import com.vianavitor.enterprisechat.dto.department.DepartmentRespDTO;
import com.vianavitor.enterprisechat.dto.team.TeamCreateDTO;
import com.vianavitor.enterprisechat.dto.team.TeamRespDTO;
import com.vianavitor.enterprisechat.dto.team.TeamUpdateDTO;
import com.vianavitor.enterprisechat.model.Team;

public class TeamDTOMapper implements CustomMapper<Team> {
    private static final TeamDTOMapper instance = new TeamDTOMapper();

    private TeamDTOMapper() {}

    public static TeamDTOMapper getInstance() {
        return instance;
    }

    @Override
    public Team createDTOtoEntity(Record cDto) {
        TeamCreateDTO dto = (TeamCreateDTO) cDto;

        Team team = new Team();
        team.setName(dto.name());

        return team;
    }

    @Override
    public Team updateDTOtoEntity(Record uDto) {
        TeamUpdateDTO dto = (TeamUpdateDTO) uDto;

        Team team = new Team();
        team.setId(dto.id());
        team.setName(dto.name());
        team.setMembersCount(dto.membersCount());

        return team;
    }

    @Override
    public TeamRespDTO entityToRespDTO(Team team) {
        DepartmentRespDTO departmentDto = DepartmentDTOMapper.getInstance()
                .entityToRespDTO(team.getDepartment());

        return new TeamRespDTO(team.getName(), departmentDto, team.getMembersCount());
    }
}
