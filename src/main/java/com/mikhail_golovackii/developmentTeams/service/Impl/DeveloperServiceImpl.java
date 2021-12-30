
package com.mikhail_golovackii.developmentTeams.service.Impl;

import com.mikhail_golovackii.developmentTeams.model.Developer;
import com.mikhail_golovackii.developmentTeams.repository.DeveloperRepository;
import com.mikhail_golovackii.developmentTeams.repository.impl.DeveloperRepositoryImpl;
import com.mikhail_golovackii.developmentTeams.service.DeveloperService;
import java.util.List;


public class DeveloperServiceImpl implements DeveloperService {

    private DeveloperRepository repository;

    public DeveloperServiceImpl() {
        this.repository = new DeveloperRepositoryImpl();
    }

    public DeveloperServiceImpl(DeveloperRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveDeveloper(Developer developer) {
        repository.save(developer);
    }

    @Override
    public Developer updateDeveloper(int id, Developer developer) {
        return repository.update(id, developer);
    }

    @Override
    public Developer getDeveloperById(int id) {
        return repository.getId(id);
    }

    @Override
    public List<Developer> getAllDevelopers() {
        return repository.getAll();
    }

    @Override
    public void deleteDeveloperById(int id) {
        repository.deleteId(id);
    }
    

}
