
package com.mikhail_golovackii.developmentTeams.service.Impl;

import com.mikhail_golovackii.developmentTeams.model.Skill;
import com.mikhail_golovackii.developmentTeams.repository.impl.SkillRepositoryImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class SkillServiceImplTest {
    
    @Mock
    private SkillRepositoryImpl repository;
    
    @InjectMocks
    private SkillServiceImpl service;
    
    private List<Skill> skills;
    private Skill skill1;
    private Skill skill2;
    private Skill skill3;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        skills = new ArrayList<>();
        skill1 = new Skill("Test1 test");
        skill2 = new Skill("Test2 test");
        skill3 = new Skill("Test3 test");
        
        Collections.addAll(skills, skill1, skill2, skill3);
        
    }

    @Test
    public void saveSkillTest() {

        doNothing().when(repository).save(skill1.getName());
        
        service.saveSkill(skill1.getName());
        
        verify(repository).save(skill1.getName());
    }

    @Test
    public void updateSkillTest() {
        Skill newSkill = new Skill("new skill");
        when(repository.update(skill1.getName(), newSkill.getName())).thenReturn(newSkill);
        
        Skill testSkill = service.updateSkill(skill1.getName(), newSkill.getName());
        
        assertEquals(newSkill, testSkill);
        assertNotNull(testSkill);
    }

    @Test
    public void getSkillByNameTest() {
        when(repository.getSkill(skill1.getName())).thenReturn(skill1);
        
        Skill testSkill = service.getSkillByName(skill1.getName());
        
        assertEquals(skill1, testSkill);
        assertNotNull(testSkill);
    }

    @Test
    public void getAllSkillsTest() {
        when(repository.getAll()).thenReturn(skills);
        
        List<Skill> testSkills = service.getAllSkills();
        
        assertSame(skills, testSkills);
    }

    @Test
    public void deleteSkillByNameTest() {
        doNothing().when(repository).delete(skill1.getName());
        
        service.deleteSkillByName(skill1.getName());
        
        verify(repository).delete(skill1.getName());
    }
    
}
