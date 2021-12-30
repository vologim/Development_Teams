
package com.mikhail_golovackii.developmentTeams.service.Impl;

import com.mikhail_golovackii.developmentTeams.model.Developer;
import com.mikhail_golovackii.developmentTeams.model.Skill;
import com.mikhail_golovackii.developmentTeams.repository.impl.DeveloperRepositoryImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;

public class DeveloperServiceImplTest {
        
    @Mock
    private DeveloperRepositoryImpl repository;
        
    @InjectMocks
    private DeveloperServiceImpl service; 
    
    private List<Skill> skills;
    private Skill skill1;
    private Skill skill2;
    private Skill skill3;
    
    private List<Developer> developers;
    private Developer dev1;
    private Developer dev2;
    private Developer dev3;

    public DeveloperServiceImplTest() {
        skills = new ArrayList<>();
        skill1 = new Skill("Java");
        skill2 = new Skill("Spring");
        skill3 = new Skill("SQL");
        skills.add(skill1);
        skills.add(skill2);
        skills.add(skill3);
        
        developers = new ArrayList<>();
        dev1 = new Developer("Misha", "Golovackii", skills);
        dev2 = new Developer("Ivan", "Petrov", skills);
        dev3 = new Developer("Ira", "Ivanova", skills);
        developers.add(dev1);
        developers.add(dev2);
        developers.add(dev3);
    }
    
    @Before
    public void initBefore() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveDeveloperTest(){        
        doNothing().when(repository).save(dev1);
        service.saveDeveloper(dev1);
        
        verify(repository).save(dev1);
    }
    
    @Test
    public void updateDeveloperTest(){
        when(repository.update(1, dev1)).thenReturn(dev1);
        
        Developer testDeveloper = service.updateDeveloper(1, dev1);
        
        assertSame(dev1, testDeveloper);
        assertNotNull(testDeveloper);
        assertEquals(skills, testDeveloper.getSkills());
        assertEquals(3, testDeveloper.getSkills().size());
    }
    
    @Test
    public void getDeveloperByIdTest(){
        dev1.setId(1);
        when(repository.getId(1)).thenReturn(dev1);
        
        Developer developer = service.getDeveloperById(1);
        
        assertSame(dev1, developer);
        assertEquals(1, developer.getId());
        assertNotNull(developer);
    }
    
    @Test
    public void getAllDevelopersTest(){
        when(repository.getAll()).thenReturn(developers);
        
        List<Developer> listDevelopers = service.getAllDevelopers();
        
        assertEquals(3, listDevelopers.size());
        assertSame(developers, listDevelopers);
    }
    
    @Test
    public void deleteDeveloperByIdTest(){
        int idDeveloper = 3;
        
        doNothing().when(repository).deleteId(idDeveloper);
        
        service.deleteDeveloperById(idDeveloper);
        
        verify(repository).deleteId(idDeveloper);
    }
    
}
