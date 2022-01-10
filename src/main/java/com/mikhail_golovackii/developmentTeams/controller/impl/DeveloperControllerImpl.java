
package com.mikhail_golovackii.developmentTeams.controller.impl;

import com.mikhail_golovackii.developmentTeams.controller.DeveloperController;
import com.mikhail_golovackii.developmentTeams.model.Developer;
import com.mikhail_golovackii.developmentTeams.service.DeveloperService;
import com.mikhail_golovackii.developmentTeams.service.Impl.DeveloperServiceImpl;
import java.util.List;

public class DeveloperControllerImpl implements DeveloperController {
    
    private final DeveloperService service = new DeveloperServiceImpl();

    @Override
    public void saveDeveloper(Developer developer) {
        service.saveDeveloper(developer);
    }

    @Override
    public Developer updateDeveloper(int id, Developer developer) {
        return service.updateDeveloper(id, developer);
    }

    @Override
    public Developer getDeveloperById(int id) {
        return service.getDeveloperById(id);
    }

    @Override
    public List<Developer> getAllDevelopers() {
        return service.getAllDevelopers();
    }

    @Override
    public void deleteDeveloperById(int id) {
        service.deleteDeveloperById(id);
    }

}
