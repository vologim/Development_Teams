
package com.mikhail_golovackii.developmentTeams.repository;

import com.mikhail_golovackii.developmentTeams.model.Developer;
import java.util.List;

public interface DeveloperRepository {
    
    void save(Developer developer);
    
    Developer update(int id, Developer developer);
    
    Developer getId(int id);
    
    List<Developer> getAll();
    
    void deleteId(int id);
    
}
