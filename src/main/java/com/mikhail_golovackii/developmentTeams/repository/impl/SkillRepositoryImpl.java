
package com.mikhail_golovackii.developmentTeams.repository.impl;

import com.mikhail_golovackii.developmentTeams.utils.DBConnectionSingleton;
import com.mikhail_golovackii.developmentTeams.model.Skill;
import com.mikhail_golovackii.developmentTeams.repository.SkillRepository;
import com.mikhail_golovackii.developmentTeams.utils.SkillQueries;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillRepositoryImpl implements SkillRepository {

    @Override
    public void save(String skill) {
        
        String query = SkillQueries.insertSkillQuery(skill);
        
        try(PreparedStatement preparedStatement = DBConnectionSingleton.preparedStatement(query)) {
            preparedStatement.executeUpdate(query);
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("Such an item exists in the database");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Skill update(String oldSkill, String newSkill) {
        Skill skill = getSkill(oldSkill);
        
        if (skill == null){
            return null;
        }

        skill.setName(newSkill);
        
        String query = SkillQueries.updateSkillQuery(skill);
        try(PreparedStatement preparedStatement = DBConnectionSingleton.preparedStatement(query)) {
            preparedStatement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return skill;
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
        
        String query = SkillQueries.getAllSkillsQuery();
        try(PreparedStatement preparedStatement = DBConnectionSingleton.preparedStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery(query);
            
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
        return skills;
    }

    @Override
    public void delete(String skill) {
        
        String query = SkillQueries.deleteSkillQuery(skill);
        
        try(PreparedStatement preparedStatement = DBConnectionSingleton.preparedStatement(query)) {
            preparedStatement.executeUpdate(query);
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
