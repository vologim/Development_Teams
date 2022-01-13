package com.mikhail_golovackii.developmentTeams.utils;

import com.mikhail_golovackii.developmentTeams.model.Developer;

public class DeveloperQueries {

    public static String insertDeveloperWithoutIdQuery(Developer developer) {
        return String.format("INSERT INTO developer (first_name, last_name) "
                + "VALUE ('%s', '%s');", developer.getFirstName(), developer.getLastName());
    }

    public static String insertDeveloperWithIdQuery(Developer developer) {
        return String.format("INSERT INTO developer (id, first_name, last_name) "
                + "VALUE ('%d', '%s', '%s');", developer.getId(), developer.getFirstName(), developer.getLastName());
    }

    public static String insertDeveloperSkillsQuery(int developerId, int skillId) {
        return String.format("INSERT INTO developer_skills VALUE (%d, %d);", developerId, skillId);
    }

    public static String getDeveloperByIdQuery(int id) {
        return String.format("SELECT * FROM developer WHERE id = %d;", id);
    }
    
    public static String getAllDevelopersQuery() {
        return "SELECT * FROM developer;";
    }
    
    public static String getSkillsDeveloperById(int id) {
        return String.format("SELECT skill.* FROM skill"
                + " JOIN developer_skills"
                + " ON developer_skills.skill_id = skill.id"
                + " JOIN developer"
                + " ON developer_skills.developer_id = developer.id"
                + " WHERE developer.id = %d", id);
    }

    public static String getAllDeveloperAndSkillsQuery() {
        return "SELECT developer.id, skill.id, skill.name FROM developer"
                + " JOIN developer_skills"
                + " ON developer.id = developer_skills.developer_id"
                + " JOIN skill"
                + " ON developer_skills.skill_id = skill.id";
    }
    
    public static String deleteDeveloperQuery(int id) {
        return "DELETE FROM developer WHERE id = " + id;
    }
}
