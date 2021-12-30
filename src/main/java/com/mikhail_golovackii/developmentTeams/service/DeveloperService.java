
package com.mikhail_golovackii.developmentTeams.service;

import com.mikhail_golovackii.developmentTeams.model.Developer;
import java.util.List;

public interface DeveloperService {

    void saveDeveloper(Developer developer);
    
    Developer updateDeveloper(int id, Developer developer);
    
    Developer getDeveloperById(int id);
    
    List<Developer> getAllDevelopers();
    
    void deleteDeveloperById(int id);
}
