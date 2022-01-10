
package com.mikhail_golovackii.developmentTeams.repository.impl;

import com.mikhail_golovackii.developmentTeams.utils.DBConnectionSingleton;
import com.mikhail_golovackii.developmentTeams.model.Developer;
import com.mikhail_golovackii.developmentTeams.model.Team;
import com.mikhail_golovackii.developmentTeams.repository.DeveloperRepository;
import com.mikhail_golovackii.developmentTeams.repository.TeamRepository;
import com.mikhail_golovackii.developmentTeams.utils.TeamQueries;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TeamRepositoryImpl implements TeamRepository {
    
    @Override
    public void save(Team team) {
        
        if (team.getId() == 0) {
            saveNewTeam(team);
            team.setId(getLastIdTeam());
        }
        else {
            saveTeam(team);
        }
        
        Set<Developer> developersFromTeam = new HashSet<>(team.getDevelopers());
        if (!developersFromTeam.isEmpty() || developersFromTeam != null) {
            
            DeveloperRepository developerRepository = new DeveloperRepositoryImpl();
            
            List<Developer> developersFromDB = developerRepository.getAll();
            Developer developer;
            String query;
            
            for (Developer elem : developersFromTeam) {
                developer = developersFromDB.stream()
                        .filter(dev -> dev.equals(elem))
                        .findFirst()
                        .orElse(null);

                if (developer != null) {
                    query = TeamQueries.insertDevelopersTeamQuery(developer.getId(), team.getId());
                    try(PreparedStatement statement = DBConnectionSingleton.preparedStatement(query)) {
                        statement.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public Team update(int id, Team team) {
        
        if (getId(id) == null){
            return null;
        }
        
        deleteId(id);
        
        save(team);
        
        return team;
    }

    @Override
    public Team getId(int id) {
        Optional<Team> team = getAll().stream().filter(elem -> elem.getId() == id)
                                               .findAny();
        
        return team.orElse(null);
    }

    @Override
    public List<Team> getAll() {
        List<Team> teams = new ArrayList<>();
        String query = TeamQueries.getAllTeamsQuery();
        
        try(PreparedStatement statement = DBConnectionSingleton.preparedStatement(query)) {            
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()){
                Team team = new Team();
                
                team.setId(resultSet.getInt("id"));
                team.setName(resultSet.getString("name"));
                teams.add(team);
            }
            
            query = TeamQueries.getAllDevelopersAndTeam();
            resultSet = statement.executeQuery(query);
            
            DeveloperRepository developerRepository = new DeveloperRepositoryImpl();
            List<Developer> developers = developerRepository.getAll();
            Optional<Team> team;
            Developer developer;
            
            while (resultSet.next()){
                int idTeam = resultSet.getInt(1);
                int idDeveloper = resultSet.getInt(2);
                
                team = teams.stream().filter(elem -> elem.getId() == idTeam)
                                     .findAny();
                developer = developers.stream().filter(elem -> elem.getId() == idDeveloper)
                                               .findAny()
                                               .get();
                
                if (team.isPresent()){
                    team.get().addDeveloper(developer);
                }
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return teams;
    }

    @Override
    public void deleteId(int id) {
        String query = TeamQueries.deleteTeamQuery(id);
        
        try(PreparedStatement statement = DBConnectionSingleton.preparedStatement(query)) {
            statement.executeUpdate(query);
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private void saveNewTeam(Team team) {
        String query = TeamQueries.insertTeamWithoutIdQuery(team);
        
        try(PreparedStatement statement = DBConnectionSingleton.preparedStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void saveTeam(Team team) {
        String query = TeamQueries.insertTeamWithIdQuery(team);
        
        try(PreparedStatement statement = DBConnectionSingleton.preparedStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private int getLastIdTeam() {
        int teamId = -1;
        String query = TeamQueries.getLastIdTeamQuery();
        
        try (PreparedStatement statement = DBConnectionSingleton.preparedStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.first();
            teamId = resultSet.getInt("id");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return teamId;
    }
}
