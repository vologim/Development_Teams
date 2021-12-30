
package com.mikhail_golovackii.developmentTeams.service.Impl;

import com.mikhail_golovackii.developmentTeams.model.Team;
import com.mikhail_golovackii.developmentTeams.repository.TeamRepository;
import com.mikhail_golovackii.developmentTeams.repository.impl.TeamRepositoryImpl;
import com.mikhail_golovackii.developmentTeams.service.TeamService;
import java.util.List;

public class TeamServiceImpl implements TeamService {

    private TeamRepository repository;

    public TeamServiceImpl() {
        repository = new TeamRepositoryImpl();
    }

    public TeamServiceImpl(TeamRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveTeam(Team team) {
        repository.save(team);
    }

    @Override
    public Team updateTeam(int id, Team team) {
        return repository.update(id, team);
    }

    @Override
    public Team getTeamById(int id) {
        return repository.getId(id);
    }

    @Override
    public List<Team> getAllTeams() {
        return repository.getAll();
    }

    @Override
    public void deleteTeamById(int id) {
        repository.deleteId(id);
    }
}
