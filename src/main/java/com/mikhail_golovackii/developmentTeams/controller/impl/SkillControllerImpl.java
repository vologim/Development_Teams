
package com.mikhail_golovackii.developmentTeams.controller.impl;

import com.mikhail_golovackii.developmentTeams.controller.SkillController;
import com.mikhail_golovackii.developmentTeams.model.Skill;
import com.mikhail_golovackii.developmentTeams.service.Impl.SkillServiceImpl;
import com.mikhail_golovackii.developmentTeams.service.SkillService;
import java.util.List;


public class SkillControllerImpl implements SkillController {

    private final SkillService service = new SkillServiceImpl();

    @Override
    public void saveSkill(String skill) {
        service.saveSkill(skill);
    }

    @Override
    public Skill updateSkill(String oldSkill, String newSkill) {
        return service.updateSkill(oldSkill, newSkill);
    }

    @Override
    public Skill getSkillByName(String skill) {
        return service.getSkillByName(skill);
    }

    @Override
    public List<Skill> getAllSkills() {
        return service.getAllSkills();
    }

    @Override
    public void deleteSkillByName(String skill) {
        service.deleteSkillByName(skill);
    }
    
}
