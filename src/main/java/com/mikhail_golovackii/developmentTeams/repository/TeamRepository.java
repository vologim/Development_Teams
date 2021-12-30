
package com.mikhail_golovackii.developmentTeams.repository;

import com.mikhail_golovackii.developmentTeams.model.Team;
import java.util.List;

public interface TeamRepository {

    void save(Team team);
    
    Team update(int id, Team team);
    
    Team getId(int id);
    
    List<Team> getAll();
    
    void deleteId(int id);
    
}
