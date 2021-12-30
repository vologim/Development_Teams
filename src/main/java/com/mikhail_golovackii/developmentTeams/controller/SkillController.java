
package com.mikhail_golovackii.developmentTeams.controller;

import com.mikhail_golovackii.developmentTeams.model.Skill;
import java.util.List;

public interface SkillController {
    
    void saveSkill(String skill);
    
    Skill updateSkill(String oldSkill, String newSkill);
    
    Skill getSkillByName(String skill);
    
    List<Skill> getAllSkills();
    
    void deleteSkillByName(String skill);
}
