
package com.mikhail_golovackii.developmentTeams.utils;

import com.mikhail_golovackii.developmentTeams.model.Skill;

public class SkillQueries {
    public static String insertSkillQuery(String skillName) {
        return String.format("INSERT INTO skill (name) VALUE ('%s');", skillName);
    }
    
    public static String updateSkillQuery(Skill skill) {
        return String.format("UPDATE skill SET name = ('%s') WHERE id = %d;", skill.getName(), skill.getId());
    }
    
    public static String getAllSkillsQuery() {
        return "SELECT * FROM skill ORDER BY id;";
    }
    
    public static String deleteSkillQuery(String skillName) {
        return String.format("DELETE FROM skill WHERE name = '%s';", skillName);
    }
}
