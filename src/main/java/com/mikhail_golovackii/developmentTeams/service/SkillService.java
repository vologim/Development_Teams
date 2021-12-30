
package com.mikhail_golovackii.developmentTeams.service;

import com.mikhail_golovackii.developmentTeams.model.Skill;
import java.util.List;

public interface SkillService {

    void saveSkill(String skill);
    
    Skill updateSkill(String oldSkill, String newSkill);
    
    Skill getSkillByName(String skill);
    
    List<Skill> getAllSkills();
    
    void deleteSkillByName(String skill);
    
}
