
package com.mikhail_golovackii.developmentTeams.view.impl;

import com.mikhail_golovackii.developmentTeams.controller.DeveloperController;
import com.mikhail_golovackii.developmentTeams.controller.impl.DeveloperControllerImpl;
import com.mikhail_golovackii.developmentTeams.model.Developer;
import com.mikhail_golovackii.developmentTeams.model.Skill;
import com.mikhail_golovackii.developmentTeams.view.DeveloperView;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeveloperViewImpl implements DeveloperView {

    private final DeveloperController controller = new DeveloperControllerImpl();
    private final Scanner scanner = new Scanner(System.in);
    private String text;
    
    @Override
    public void createDeveloper() {
        Developer developer = new Developer();
        
        System.out.println("Create developer:");
        
        developer = createOrUpdateDeveloper(developer);
        
        if (developer == null){
            System.out.println("Developer is not created");
            return;
        }
        
        controller.saveDeveloper(developer);
        
        System.out.println("Developer is created");
    }

    @Override
    public void updateDeveloper() {
        Developer developer;
        
        System.out.println("Enter id developer: ");
        
        if (scanner.hasNextInt()){
            developer = controller.getDeveloperById(scanner.nextInt());
            if (developer == null){
                System.out.println("Developer not found");
                return;
            }
            
            developer = createOrUpdateDeveloper(developer);
            if (developer == null){
                System.out.println("Developer is not created");
            return;
            }
        
            controller.updateDeveloper(developer.getId(), developer);
            
            System.out.println("Developer is updated");
            return;
        }
        System.out.println("Error. Write number!");
       
    }

    @Override
    public void getDeveloperById() {
        System.out.println("Enter id developer: ");
        
        if (scanner.hasNextInt()){
            System.out.println(controller.getDeveloperById(scanner.nextInt()));
            return;
        }
        System.out.println("Error. Write number!");
    }

    @Override
    public void getAllDevelopers() {
        controller.getAllDevelopers().forEach(elem -> System.out.println(elem));
    }

    @Override
    public void deleteDeveloperById() {
        System.out.println("Write id developer: ");
        
        if (scanner.hasNextInt()){
            controller.deleteDeveloperById(scanner.nextInt());
            System.out.println("Developer is deleted");
            return;
        }
        System.out.println("Error. Write number!");
    }

    private Developer createOrUpdateDeveloper(Developer developer){
        List<Skill> skills = new ArrayList<>();
        
        System.out.println("enter first name: ");
        text = scanner.nextLine();
        developer.setFirstName(text);
        
        System.out.println("enter last name: ");
        text = scanner.nextLine();
        developer.setLastName(text);
        
        System.out.println("enter skill name or write in exit to exit:");
        while (true){
            System.out.println("skill: ");
            text = scanner.nextLine();
            
            if (text.toLowerCase().equals("exit")){
                break;
            }
            
            skills.add(new Skill(text));
        }
        
        if (skills.isEmpty()){
            System.out.println("The developer must have the skills!");
            return null;
        }
        
        developer.setSkills(skills);
        
        return developer;
    }
}
