
package com.mikhail_golovackii.developmentTeams.repository.impl;

import com.mikhail_golovackii.developmentTeams.databaseConnetction.DatabaseConnectionSingletonImpl;
import com.mikhail_golovackii.developmentTeams.model.Developer;
import com.mikhail_golovackii.developmentTeams.model.Skill;
import com.mikhail_golovackii.developmentTeams.repository.DeveloperRepository;
import com.mikhail_golovackii.developmentTeams.repository.SkillRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeveloperRepositoryImpl implements DeveloperRepository {
    
    private DatabaseConnectionSingletonImpl DBConnection = DatabaseConnectionSingletonImpl.getInstance();
    private Statement statement = null;
    private ResultSet resultSet = null;
    private String query;
    
    @Override
    public void save(Developer developer) {
        SkillRepository skillRepository = new SkillRepositoryImpl();
        
        try {
            
            int developerId = 0;
            statement = DBConnection.getConnection().createStatement();
            if (developer.getId() == 0){
                query = "INSERT INTO developer (first_name, last_name) "
                        + "VALUE ('" + developer.getFirstName() + "', '" + developer.getLastName() + "')";
                statement.executeUpdate(query);

                query = "SELECT id FROM developer ORDER BY id DESC LIMIT 1";
                statement = DBConnection.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                resultSet = statement.executeQuery(query);
                resultSet.first();
                developerId = resultSet.getInt("id");
            }
            else {
                developerId = developer.getId();
                query = "INSERT INTO developer (id, first_name, last_name) "
                        + "VALUE ('" + developerId + "', '" + developer.getFirstName() + "', '" + developer.getLastName() + "')";
                statement.executeUpdate(query);
            }

            List<Skill> skillsFromDeveloper = developer.getSkills();
            if (!skillsFromDeveloper.isEmpty() || skillsFromDeveloper != null){
                
                List<Skill> skillsFromDB = skillRepository.getAll();
                List<String> skillsString = skillsFromDB.stream()
                                                        .map(elem -> elem.getName().toLowerCase())
                                                        .collect(Collectors.toList());

                for (Skill elem : skillsFromDeveloper){
                    
                    if (skillsString.contains(elem.getName().toLowerCase())){
                        int skillId = skillRepository.getSkill(elem.getName()).getId();
                        query = "INSERT INTO developer_skills VALUE (" + developerId + ", " + skillId + ")";
                    }
                    else {
                        skillRepository.save(elem.getName());
                        int newSkillId = skillRepository.getSkill(elem.getName()).getId();
                        query = "INSERT INTO developer_skills VALUE (" + developerId + ", " + newSkillId + ")";
                    }
                    statement.executeUpdate(query);
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
    public Developer update(int id, Developer developer) {
        Developer dev = getId(id);
        
        if (dev == null){
            return null;
        }
        
        deleteId(id);
        
        dev.setId(id);
        dev.setFirstName(developer.getFirstName());
        dev.setLastName(developer.getLastName());
        dev.setSkills(developer.getSkills());
        
        save(dev);
        
        return dev;
    }

    @Override
    public Developer getId(int id) {
        Optional<Developer> developer = getAll().stream()
                                                .filter(elem -> elem.getId() == id)
                                                .findFirst();
        
        if (developer.isPresent()){
            return developer.get();
        }
        
        return null;
    }

    @Override
    public List<Developer> getAll() {
        List<Developer> developers = new ArrayList<>();
        
        try {            
            statement = DBConnection.getConnection().createStatement();
            query = "SELECT * FROM developer;";
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()){
                Developer developer = new Developer();
                
                developer.setId(resultSet.getInt("id"));
                developer.setFirstName(resultSet.getString("first_name"));
                developer.setLastName(resultSet.getString("last_name"));
                developers.add(developer);
            }
            
            query = "SELECT developer.id, skill.id, skill.name FROM developer"
                    + " JOIN developer_skills"
                    + " ON developer.id = developer_skills.developer_id"
                    + " JOIN skill"
                    + " ON developer_skills.skill_id = skill.id";
            
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()){
                Skill skill = new Skill();
                                
                int developerId = resultSet.getInt(1);
                skill.setId(resultSet.getInt(2));
                skill.setName(resultSet.getString(3));
                           
                Optional<Developer> dev = developers.stream()
                                                    .filter(elem -> elem.getId() == developerId)
                                                    .findFirst();
                
                if (dev.isPresent()){
                    dev.get().addSkill(skill);
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

        return developers;
    }

    @Override
    public void deleteId(int id) {
        try {
            statement = DBConnection.getConnection().createStatement();
            query = "DELETE FROM developer WHERE id = " + id;
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
