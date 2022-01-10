package com.mikhail_golovackii.developmentTeams.repository.impl;

import com.mikhail_golovackii.developmentTeams.utils.DBConnectionSingleton;
import com.mikhail_golovackii.developmentTeams.model.Developer;
import com.mikhail_golovackii.developmentTeams.model.Skill;
import com.mikhail_golovackii.developmentTeams.repository.DeveloperRepository;
import com.mikhail_golovackii.developmentTeams.repository.SkillRepository;
import com.mikhail_golovackii.developmentTeams.utils.DeveloperQueries;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DeveloperRepositoryImpl implements DeveloperRepository {

    @Override
    public void save(Developer developer) {
        
        if (developer.getId() == 0) {
            saveNewDeveloper(developer);
            developer.setId(getLastIdDeveloper());
        } 
        else {
            saveDeveloper(developer);
        }

        List<Skill> skillsFromDeveloper = developer.getSkills();
        if (!skillsFromDeveloper.isEmpty() || skillsFromDeveloper != null) {

            SkillRepository skillRepository = new SkillRepositoryImpl();
            
            List<Skill> skillsFromDB = skillRepository.getAll();
            Set<String> skillsString = skillsFromDB.stream()
                    .map(elem -> elem.getName().toLowerCase())
                    .collect(Collectors.toSet());

            String query;
            for (Skill elem : skillsFromDeveloper) {

                if (skillsString.contains(elem.getName().toLowerCase())) {
                    int skillId = skillRepository.getSkill(elem.getName()).getId();
                    query = DeveloperQueries.insertDeveloperSkillsQuery(developer.getId(), skillId);
                } else {
                    skillRepository.save(elem.getName());
                    int newSkillId = skillRepository.getSkill(elem.getName()).getId();
                    query = DeveloperQueries.insertDeveloperSkillsQuery(developer.getId(), newSkillId);
                }
                
                try(PreparedStatement statement = DBConnectionSingleton.preparedStatement(query)) {
                    statement.executeUpdate(query);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public Developer update(int id, Developer developer) {
        Developer dev = getId(id);

        if (dev == null) {
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

        if (developer.isPresent()) {
            return developer.get();
        }

        return null;
    }

    @Override
    public List<Developer> getAll() {
        List<Developer> developers = new ArrayList<>();
        String query = DeveloperQueries.getAllDevelopersQuery();
        
        try(PreparedStatement statement = DBConnectionSingleton.preparedStatement(query)) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Developer developer = new Developer();

                developer.setId(resultSet.getInt("id"));
                developer.setFirstName(resultSet.getString("first_name"));
                developer.setLastName(resultSet.getString("last_name"));
                developers.add(developer);
            }

            query = DeveloperQueries.getAllDeveloperAndSkillsQuery();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Skill skill = new Skill();

                int developerId = resultSet.getInt(1);
                skill.setId(resultSet.getInt(2));
                skill.setName(resultSet.getString(3));

                Optional<Developer> dev = developers.stream()
                        .filter(elem -> elem.getId() == developerId)
                        .findFirst();

                if (dev.isPresent()) {
                    dev.get().addSkill(skill);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return developers;
    }

    @Override
    public void deleteId(int id) {
        String query = DeveloperQueries.deleteDeveloperQuery(id);
        
        try(PreparedStatement statement = DBConnectionSingleton.preparedStatement(query)) {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void saveNewDeveloper(Developer developer) {
        String query = DeveloperQueries.insertDeveloperWithoutIdQuery(developer);

        try ( PreparedStatement statement = DBConnectionSingleton.preparedStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void saveDeveloper(Developer developer) {
        String query = DeveloperQueries.insertDeveloperWithIdQuery(developer);

        try (PreparedStatement statement = DBConnectionSingleton.preparedStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private int getLastIdDeveloper() {
        int developerId = -1;
        String query = DeveloperQueries.getLastIdDeveloperQuery();
        
        try (PreparedStatement statement = DBConnectionSingleton.preparedStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.first();
            developerId = resultSet.getInt("id");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return developerId;
    }
}
