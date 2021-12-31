
package com.mikhail_golovackii.developmentTeams;

import com.mikhail_golovackii.developmentTeams.databaseConnetction.DatabaseConnectionSingletonImpl;
import com.mikhail_golovackii.developmentTeams.view.DeveloperView;
import com.mikhail_golovackii.developmentTeams.view.SkillView;
import com.mikhail_golovackii.developmentTeams.view.TeamView;
import com.mikhail_golovackii.developmentTeams.view.impl.DeveloperViewImpl;
import com.mikhail_golovackii.developmentTeams.view.impl.SkillViewImpl;
import com.mikhail_golovackii.developmentTeams.view.impl.TeamViewImpl;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        
        System.out.println("Hello");
        
        final DeveloperView developerView = new DeveloperViewImpl();
        final SkillView skillView = new SkillViewImpl();
        final TeamView teamView = new TeamViewImpl();
        
        Scanner scanner = new Scanner(System.in);
        String check;
        
        while (true){
            System.out.println("Enter:");
            System.out.println("1: create: developer, skill or team");
            System.out.println("2: show: developer, skill or team");
            System.out.println("3: update: developer, skill or team");
            System.out.println("4: delete: developer, skill or team");
            System.out.println("5: customization database");
            System.out.println("exit: exit the application");
            
            System.out.println("input: ");
            check = scanner.nextLine();
            
            switch (check.toLowerCase()){
                case "1":
                    System.out.println("Create:");
                    System.out.println("1: developer");
                    System.out.println("2: skill");
                    System.out.println("3: team");
                    System.out.println("enter any value to exit");
                    
                    System.out.println("input: ");
                    check = scanner.nextLine();
                        
                    switch (check.toLowerCase()){
                        case "1":
                            developerView.createDeveloper();
                            break;
                        case "2":
                            skillView.createSkill();
                            break;
                        case "3":
                            teamView.createTeam();
                            break;
                        default:
                            System.out.println("Element not created");
                            break;    
                    }
                    break;
                case "2":
                    System.out.println("Show:");
                    System.out.println("1: developer");
                    System.out.println("2: skill");
                    System.out.println("3: team");
                    System.out.println("4: all developers");
                    System.out.println("5: all skills");
                    System.out.println("6: all teams");
                    System.out.println("enter any value to exit");
                    
                    System.out.println("input: ");
                    check = scanner.nextLine();
                        
                    switch (check.toLowerCase()){
                        case "1":
                            developerView.getDeveloperById();
                            break;
                        case "2":
                            skillView.getSkillByName();
                            break;
                        case "3":
                            teamView.getTeamById();
                            break;
                        case "4":
                            developerView.getAllDevelopers();
                            break;
                        case "5":
                            skillView.getAllSkills();
                            break;
                        case "6":
                            teamView.getAllTeams();
                            break;
                        default:
                            System.out.println("Element not created");
                            break;    
                    }
                    break;
                case "3":
                    System.out.println("Update:");
                    System.out.println("1: developer");
                    System.out.println("2: skill");
                    System.out.println("3: team");
                    System.out.println("enter any value to exit");
                    
                    System.out.println("input: ");
                    check = scanner.nextLine();
                        
                    switch (check.toLowerCase()){
                        case "1":
                            developerView.updateDeveloper();
                            break;
                        case "2":
                            skillView.updateSkill();
                            break;
                        case "3":
                            teamView.updateTeam();
                            break;                        
                        default:
                            System.out.println("Element not created");
                            break;    
                    }
                    break;
                case "4":
                    System.out.println("Delete:");
                    System.out.println("1: developer");
                    System.out.println("2: skill");
                    System.out.println("3: team");
                    System.out.println("enter any value to exit");
                    
                    System.out.println("input: ");
                    check = scanner.nextLine();
                        
                    switch (check.toLowerCase()){
                        case "1":
                            developerView.deleteDeveloperById();
                            break;
                        case "2":
                            skillView.deleteSkillByName();
                            break;
                        case "3":
                            teamView.deleteTeamById();
                            break;
                        default:
                            System.out.println("Element not created");
                            break;    
                    }
                    break;
                case "5":
                    System.out.println("Enter user database:");
                    check = scanner.nextLine();
                    DatabaseConnectionSingletonImpl.getInstance().setUser(check);
                    
                    System.out.println("Enter user password:");
                    check = scanner.nextLine();
                    DatabaseConnectionSingletonImpl.getInstance().setUser(check);
                    break;
                case "exit":
                    System.out.println("Bye!");
                    return;
                default:
                    break;  
            }
        }

    }
  
}
