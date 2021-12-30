
package com.mikhail_golovackii.developmentTeams.service.Impl;

import com.mikhail_golovackii.developmentTeams.model.Skill;
import com.mikhail_golovackii.developmentTeams.repository.SkillRepository;
import com.mikhail_golovackii.developmentTeams.repository.impl.SkillRepositoryImpl;
import com.mikhail_golovackii.developmentTeams.service.SkillService;
import java.util.List;


public class SkillServiceImpl implements SkillService {

    private SkillRepository repository;

    public SkillServiceImpl() {
        this.repository = new SkillRepositoryImpl();
    }

    public SkillServiceImpl(SkillRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveSkill(String skill) {
        repository.save(skill);
    }

    @Override
    public Skill updateSkill(String oldSkill, String newSkill) {
        return repository.update(oldSkill, newSkill);
    }

    @Override
    public Skill getSkillByName(String skill) {
        return repository.getSkill(skill);
    }

    @Override
    public List<Skill> getAllSkills() {
        return repository.getAll();
    }

    @Override
    public void deleteSkillByName(String skill) {
        repository.delete(skill);
    }

}
