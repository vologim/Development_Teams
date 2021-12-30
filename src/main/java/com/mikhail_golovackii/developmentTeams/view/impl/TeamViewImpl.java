
package com.mikhail_golovackii.developmentTeams.view.impl;

import com.mikhail_golovackii.developmentTeams.controller.DeveloperController;
import com.mikhail_golovackii.developmentTeams.controller.TeamController;
import com.mikhail_golovackii.developmentTeams.controller.impl.DeveloperControllerImpl;
import com.mikhail_golovackii.developmentTeams.controller.impl.TeamControllerImpl;
import com.mikhail_golovackii.developmentTeams.model.Developer;
import com.mikhail_golovackii.developmentTeams.model.Team;
import com.mikhail_golovackii.developmentTeams.view.TeamView;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TeamViewImpl implements TeamView {
    
    private final TeamController controller = new TeamControllerImpl();
    private final DeveloperController developerController = new DeveloperControllerImpl();
    private final Scanner scanner = new Scanner(System.in);
    private String text;

    @Override
    public void createTeam() {
        Team team = new Team();
        
        System.out.println("Create team:");
        
        team = createOrUpdateTeam(team);
        
        if (team == null){
            System.out.println("Team not created");
            return;
        }
        
        controller.saveTeam(team);
        
        System.out.println("Team is created");
    }

    @Override
    public void updateTeam() {
        Team team;
        
        System.out.println("Enter id team:");
        
        if (scanner.hasNextInt()){
            team = controller.getTeamById(scanner.nextInt());
            if (team == null){
                System.out.println("Team not found");
                return;
            }
            
            team = createOrUpdateTeam(team);
            if (team == null){
                System.out.println("Developer is not created");
                return;
            }
        
            controller.updateTeam(team.getId(), team);
            
            System.out.println("Team is updated");
            return;
        }
        System.out.println("Error. Write number!");
    }

    @Override
    public void getTeamById() {
        System.out.println("Enter id team: ");
        
        if (scanner.hasNextInt()){
            System.out.println(controller.getTeamById(scanner.nextInt()));
            return;
        }
        System.out.println("Error. Write number!");
    }

    @Override
    public void getAllTeams() {
        controller.getAllTeams().forEach(elem -> System.out.println(elem));
    }

    @Override
    public void deleteTeamById() {
        System.out.println("Enter id team: ");
        
        if (scanner.hasNextInt()){
            controller.deleteTeamById(scanner.nextInt());
            System.out.println("Team is deleted");
            return;
        }
        System.out.println("Error. Write number!");
    }
    
    private Team createOrUpdateTeam(Team team){
        List<Developer> developers = developerController.getAllDevelopers();
        List<Developer> newSkillsDeveloper = new ArrayList<>();
        Developer developer = new Developer();
        
        System.out.println("Enter team name: ");
        text = scanner.nextLine();
        team.setName(text);
        
        System.out.println("enter id developer or write in exit to exit:");
        while (true){
            text = scanner.next();
            
            if (text.toLowerCase().equals("exit")){
                break;
            }
            
            if (text.matches("\\d+")){
                int developerId = Integer.parseInt(text);
                
                developer = developers.stream()
                        .filter(elem -> elem.getId() == developerId)
                        .findAny()
                        .orElse(null);
                        
            }
            
            if (developer == null){
                System.out.println("Developer not found");
            }
            else {
                newSkillsDeveloper.add(developer);
                developer = null;
            }
        }
        
        if (newSkillsDeveloper.isEmpty()){
            System.out.println("The team must have the developers!");
            return null;
        }
        
        team.setDevelopers(newSkillsDeveloper);
        
        return team;
    }

}
