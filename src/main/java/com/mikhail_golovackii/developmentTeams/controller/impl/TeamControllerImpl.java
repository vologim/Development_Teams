
package com.mikhail_golovackii.developmentTeams.controller.impl;

import com.mikhail_golovackii.developmentTeams.controller.TeamController;
import com.mikhail_golovackii.developmentTeams.model.Team;
import com.mikhail_golovackii.developmentTeams.service.Impl.TeamServiceImpl;
import com.mikhail_golovackii.developmentTeams.service.TeamService;
import java.util.List;


public class TeamControllerImpl implements TeamController {

    private final TeamService service = new TeamServiceImpl();

    @Override
    public void saveTeam(Team team) {
        service.saveTeam(team);
    }

    @Override
    public Team updateTeam(int id, Team team) {
        return service.updateTeam(id, team);
    }

    @Override
    public Team getTeamById(int id) {
        return service.getTeamById(id);
    }

    @Override
    public List<Team> getAllTeams() {
        return service.getAllTeams();
    }

    @Override
    public void deleteTeamById(int id) {
        service.deleteTeamById(id);
    }
}
