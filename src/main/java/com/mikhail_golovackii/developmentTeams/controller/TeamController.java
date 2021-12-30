
package com.mikhail_golovackii.developmentTeams.controller;

import com.mikhail_golovackii.developmentTeams.model.Team;
import java.util.List;

public interface TeamController {
    
    void saveTeam(Team team);
    
    Team updateTeam(int id, Team team);
    
    Team getTeamById(int id);
    
    List<Team> getAllTeams();
    
    void deleteTeamById(int id);
}
