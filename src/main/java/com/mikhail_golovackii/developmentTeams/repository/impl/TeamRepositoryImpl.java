
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
            team = saveNewTeam(team);
        }
        else {
            team = saveTeam(team);
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
        Team team = new Team();
        String query = TeamQueries.getTeamById(id);
        
        try (PreparedStatement statement = DBConnectionSingleton.preparedStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                team.setId(resultSet.getInt("id"));
                team.setName(resultSet.getString("name"));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        query = TeamQueries.getTeamDevelopersById(id);
        try (PreparedStatement statement = DBConnectionSingleton.preparedStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            List<Developer> developers = new ArrayList<>();
            
            while (resultSet.next()) {
                Developer developer = new Developer();
                
                developer.setId(resultSet.getInt(1));
                developer.setFirstName(resultSet.getString(2));
                developer.setLastName(resultSet.getString(3));
                
                developers.add(developer);
            }
            
            team.setDevelopers(developers);
            return team;
            
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        
        return null;
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
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return teams;
    }

    @Override
    public void deleteId(int id) {
        String query = TeamQueries.deleteTeamQuery(id);
        
        try(PreparedStatement statement = DBConnectionSingleton.preparedStatement(query)) {
            statement.executeUpdate(query);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private Team saveNewTeam(Team team) {
        String query = TeamQueries.insertTeamWithoutIdQuery(team);
        
        try(PreparedStatement statement = DBConnectionSingleton.preparedStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return team;
    }

    private Team saveTeam(Team team) {
        String query = TeamQueries.insertTeamWithIdQuery(team);
        
        try(PreparedStatement statement = DBConnectionSingleton.preparedStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return team;
    }
}
