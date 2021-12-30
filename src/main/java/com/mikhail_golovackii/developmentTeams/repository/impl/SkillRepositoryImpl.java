
package com.mikhail_golovackii.developmentTeams.repository.impl;

import com.mikhail_golovackii.developmentTeams.databaseConnetction.DatabaseConnectionSingletonImpl;
import com.mikhail_golovackii.developmentTeams.model.Skill;
import com.mikhail_golovackii.developmentTeams.repository.SkillRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class SkillRepositoryImpl implements SkillRepository {

    private DatabaseConnectionSingletonImpl DBConnection = DatabaseConnectionSingletonImpl.getInstance();
    private Statement statement = null;
    private ResultSet resultSet = null;
    private String query;

    @Override
    public void save(String skill) {
        
        try {
            statement = DBConnection.getConnection().createStatement();
            query = "INSERT INTO skill (name) VALUE ('" + skill + "');";
            statement.executeUpdate(query);
        }
        catch (SQLIntegrityConstraintViolationException ex){
            System.out.println("Such an item exists in the database");
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

    @Override
    public Skill update(String oldSkill, String newSkill) {
        Skill skillName = getSkill(oldSkill);
        
        if (skillName == null){
            return null;
        }

        skillName.setName(newSkill);
        
        try {
            statement = DBConnection.getConnection().createStatement();
            query = "UPDATE skill"
                    + " SET name = '" + newSkill + "'"
                    + " WHERE id = " + skillName.getId();
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
        
        return skillName;
    }

    @Override
    public Skill getSkill(String skill) {
        Optional<Skill> skillName = getAll().stream()
                                  .filter(elem -> elem.getName().toLowerCase().equals(skill.toLowerCase()))
                                  .findFirst();
        
        if (skillName.isPresent()){
            return skillName.get();
        }
        
        return null;
    }

    @Override
    public List<Skill> getAll() {
        List<Skill> skills = new ArrayList<>();
        
        try {
            statement = DBConnection.getConnection().createStatement();
            query = "SELECT * FROM skill ORDER BY id";
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()){
                Skill skill = new Skill();
                
                skill.setId(resultSet.getInt("id"));
                skill.setName(resultSet.getString("name"));
                
                skills.add(skill);
            }
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
        return skills;
    }

    @Override
    public void delete(String skill) {
        
        try {
            statement = DBConnection.getConnection().createStatement();
            query = "DELETE FROM skill WHERE name = '" + skill + "'";
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
