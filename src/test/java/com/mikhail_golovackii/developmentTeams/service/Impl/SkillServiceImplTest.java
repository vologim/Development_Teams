
package com.mikhail_golovackii.developmentTeams.service.Impl;

import com.mikhail_golovackii.developmentTeams.model.Skill;
import com.mikhail_golovackii.developmentTeams.repository.impl.SkillRepositoryImpl;
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
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);        
    }

    @Test
    public void saveSkillTest() {

        doNothing().when(repository).save(getSkill().getName());
        
        service.saveSkill(getSkill().getName());
        
        verify(repository).save(getSkill().getName());
    }

    @Test
    public void updateSkillTest() {
        Skill newSkill = new Skill("new skill");
        when(repository.update(getSkill().getName(), newSkill.getName())).thenReturn(newSkill);
        
        Skill testSkill = service.updateSkill(getSkill().getName(), newSkill.getName());
        
        assertEquals(newSkill, testSkill);
        assertNotNull(testSkill);
    }

    @Test
    public void getSkillByNameTest() {
        when(repository.getSkill(getSkill().getName())).thenReturn(getSkill());
        
        Skill testSkill = service.getSkillByName(getSkill().getName());
        
        assertEquals(getSkill(), testSkill);
        assertNotNull(testSkill);
    }

    @Test
    public void getAllSkillsTest() {
        when(repository.getAll()).thenReturn(getSkills());
        
        List<Skill> testSkills = service.getAllSkills();
        
        assertEquals(getSkills(), testSkills);
    }

    @Test
    public void deleteSkillByNameTest() {
        doNothing().when(repository).delete(getSkill().getName());
        
        service.deleteSkillByName(getSkill().getName());
        
        verify(repository).delete(getSkill().getName());
    }
    
    private List<Skill> getSkills() {
        return List.of(new Skill("Test 1"), new Skill("Test 2"), new Skill("Test 3"));
    }
    
    private Skill getSkill() {
        return new Skill("Test 1");
    }
}
