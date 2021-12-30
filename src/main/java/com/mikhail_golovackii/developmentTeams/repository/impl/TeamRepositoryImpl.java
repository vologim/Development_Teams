
package com.mikhail_golovackii.developmentTeams.repository.impl;

import com.mikhail_golovackii.developmentTeams.databaseConnetction.DatabaseConnectionSingletonImpl;
import com.mikhail_golovackii.developmentTeams.model.Developer;
import com.mikhail_golovackii.developmentTeams.model.Team;
import com.mikhail_golovackii.developmentTeams.repository.DeveloperRepository;
import com.mikhail_golovackii.developmentTeams.repository.TeamRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class TeamRepositoryImpl implements TeamRepository {

    private DatabaseConnectionSingletonImpl DBConnection = DatabaseConnectionSingletonImpl.getInstance();
    private Statement statement = null;
    private ResultSet resultSet = null;
    private String query;
    private DeveloperRepository developerRepository = new DeveloperRepositoryImpl();
    
    @Override
    public void save(Team team) {
        
        try {
            
            int teamId = 0;
            statement = DBConnection.getConnection().createStatement();
            if (team.getId() == 0){
                query = "INSERT INTO team (name) "
                        + "VALUE ('" + team.getName() + "')";
                statement.executeUpdate(query);

                query = "SELECT id FROM team ORDER BY id DESC LIMIT 1";
                statement = DBConnection.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                resultSet = statement.executeQuery(query);
                resultSet.first();
                teamId = resultSet.getInt("id");
            }
            else {
                teamId = team.getId();
                query = "INSERT INTO team (id, name) "
                        + "VALUE ('" + teamId + "', '" + team.getName() + "')";
                statement.executeUpdate(query);
            }

            Set<Developer> developersFromTeam = new HashSet<>(team.getDevelopers());
            if (!developersFromTeam.isEmpty() || developersFromTeam != null){
                
                List<Developer> developersFromDB = developerRepository.getAll();
                Developer developer;
                
                for (Developer elem : developersFromTeam){
                    developer = developersFromDB.stream()
                                                .filter(dev -> dev.equals(elem))
                                                .findFirst()
                                                .orElse(null);
                    
                    if (developer != null){
                        query = "INSERT INTO developers_team VALUE (" + elem.getId() + ", " + teamId + ")";
                        statement.executeUpdate(query);
                    }
                }
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally { 
            try {
                statement.close();
                resultSet.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
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
        
        try {            
            statement = DBConnection.getConnection().createStatement();
            query = "SELECT * FROM team";
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()){
                Team team = new Team();
                
                team.setId(resultSet.getInt("id"));
                team.setName(resultSet.getString("name"));
                teams.add(team);
            }
            
            query = "SELECT team.id, developer.id FROM team"
                    + " JOIN developers_team"
                    + " ON developers_team.team_id = team.id"
                    + " JOIN developer"
                    + " ON developers_team.developer_id = developer.id";
            
            resultSet = statement.executeQuery(query);
            
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
        finally{
            try {
                statement.close();
                resultSet.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return teams;
    }

    @Override
    public void deleteId(int id) {
        try {
            statement = DBConnection.getConnection().createStatement();
            query = "DELETE FROM team WHERE id = " + id;
            statement.executeUpdate(query);
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally{
            try {
                statement.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
