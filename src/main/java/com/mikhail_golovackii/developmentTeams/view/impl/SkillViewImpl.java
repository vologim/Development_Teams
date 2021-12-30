
package com.mikhail_golovackii.developmentTeams.view.impl;

import com.mikhail_golovackii.developmentTeams.controller.SkillController;
import com.mikhail_golovackii.developmentTeams.controller.impl.SkillControllerImpl;
import com.mikhail_golovackii.developmentTeams.model.Skill;
import com.mikhail_golovackii.developmentTeams.view.SkillView;
import java.util.Scanner;

public class SkillViewImpl implements SkillView {

    private final SkillController controller = new SkillControllerImpl();
    private final Scanner scanner = new Scanner(System.in);
    private String text;
    
    @Override
    public void createSkill() {
        
        System.out.println("Create skill:");
        System.out.println("Enter name skill: ");
        
        text = scanner.nextLine();
        
        controller.saveSkill(text);
        
        System.out.println("Skill is created");
    }

    @Override
    public void updateSkill() {
        
        System.out.println("Update skill:");
        System.out.println("Enter old name skill: ");
        String oldName = scanner.next();
        
        if (checkSkillIsNull(oldName) == null){
            return;
        }

        System.out.println("Enter new name skill: ");
        text = scanner.nextLine();
        
        controller.updateSkill(oldName, text);
        
        System.out.println("Skill is updated");
    }

    @Override
    public void getSkillByName() {
        System.out.println("Enter name skill: ");
        text = scanner.nextLine();
        
        System.out.println(controller.getSkillByName(text));
    }

    @Override
    public void getAllSkills() {
        controller.getAllSkills().forEach(elem -> System.out.println(elem));
    }

    @Override
    public void deleteSkillByName() {
        System.out.println("Enter name skill: ");
        text = scanner.nextLine();
        
        if (checkSkillIsNull(text) == null){
            return;
        }
        
        controller.deleteSkillByName(text);
        System.out.println("Skill is deleted");
    }

    private Skill checkSkillIsNull(String skillName){
        Skill skill = controller.getSkillByName(skillName);
        
        if (skill == null){
            System.out.println("Skill not found");
            return null;
        }
        
        return skill;
    }
}
