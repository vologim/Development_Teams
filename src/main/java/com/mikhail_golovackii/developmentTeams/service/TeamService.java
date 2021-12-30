
package com.mikhail_golovackii.developmentTeams.service;

import com.mikhail_golovackii.developmentTeams.model.Team;
import java.util.List;

public interface TeamService {

    void saveTeam(Team team);
    
    Team updateTeam(int id, Team team);
    
    Team getTeamById(int id);
    
    List<Team> getAllTeams();
    
    void deleteTeamById(int id);
}
