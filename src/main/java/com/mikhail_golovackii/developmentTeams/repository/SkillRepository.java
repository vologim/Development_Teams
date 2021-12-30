
package com.mikhail_golovackii.developmentTeams.repository;

import com.mikhail_golovackii.developmentTeams.model.Skill;
import java.util.List;

public interface SkillRepository {
    
    void save(String skill);
    
    Skill update(String oldSkill, String newSkill);
    
    Skill getSkill(String skill);
    
    List<Skill> getAll();
    
    void delete(String skill);
    
}
