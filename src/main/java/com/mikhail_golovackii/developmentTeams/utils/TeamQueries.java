
package com.mikhail_golovackii.developmentTeams.utils;

import com.mikhail_golovackii.developmentTeams.model.Team;

public class TeamQueries {
    
    public static String insertTeamWithoutIdQuery(Team team) {
        return String.format("INSERT INTO team (name) VALUE ('%s');", team.getName());
    }
    
    public static String insertTeamWithIdQuery(Team team) {
        return String.format("INSERT INTO team (id, name) "
                + "VALUE ('%d', '%s');", team.getId(), team.getName());
    }
    
    public static String insertDevelopersTeamQuery(int developerId, int teamId) {
        return String.format("INSERT INTO developers_team VALUE (%d, %d);", developerId, teamId);
    }
    
    public static String getTeamById(int teamId) {
        return String.format("SELECT * FROM team WHERE id = %d", teamId);
    }
    
    public static String getTeamDevelopersById(int teamId) {
        return String.format("SELECT developer.* FROM developer"
                + " JOIN developers_team"
                + " ON developers_team.developer_id = developer.id"
                + " JOIN team"
                + " ON developers_team.team_id = team.id"
                + " WHERE team.id = %d", teamId);
    }
    
    public static String getAllTeamsQuery() {
        return "SELECT * FROM team;";
    }
    
    public static String getAllDevelopersAndTeam() {
        return "SELECT team.id, developer.id FROM team"
                    + " JOIN developers_team"
                    + " ON developers_team.team_id = team.id"
                    + " JOIN developer"
                    + " ON developers_team.developer_id = developer.id;";
    }
    
    public static String deleteTeamQuery(int id) {
        return "DELETE FROM team WHERE id = " + id;
    }
}
