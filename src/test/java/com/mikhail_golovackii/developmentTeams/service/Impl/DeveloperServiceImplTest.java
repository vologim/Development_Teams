
package com.mikhail_golovackii.developmentTeams.service.Impl;

import com.mikhail_golovackii.developmentTeams.model.Developer;
import com.mikhail_golovackii.developmentTeams.model.Skill;
import com.mikhail_golovackii.developmentTeams.repository.impl.DeveloperRepositoryImpl;
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
    
    @Before
    public void initBefore() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveDeveloperTest(){        
        doNothing().when(repository).save(getDeveloper());
        service.saveDeveloper(getDeveloper());
        
        verify(repository).save(getDeveloper());
    }
    
    @Test
    public void updateDeveloperTest(){
        when(repository.update(1, getDeveloper())).thenReturn(getDeveloper());
        
        Developer testDeveloper = service.updateDeveloper(1, getDeveloper());
        
        assertEquals(getDeveloper(), testDeveloper);
        assertNotNull(testDeveloper);
        assertEquals(getSkills(), testDeveloper.getSkills());
        assertEquals(3, testDeveloper.getSkills().size());
    }
    
    @Test
    public void getDeveloperByIdTest(){
        Developer developer = getDeveloper();
        developer.setId(1);
        when(repository.getId(1)).thenReturn(developer);
        
        Developer checkDeveloper = service.getDeveloperById(1);
        
        assertEquals(developer, checkDeveloper);
        assertEquals(1, checkDeveloper.getId());
        assertNotNull(checkDeveloper);
    }
    
    @Test
    public void getAllDevelopersTest(){
        when(repository.getAll()).thenReturn(getDevelopers());
        
        List<Developer> listDevelopers = service.getAllDevelopers();
        
        assertEquals(3, listDevelopers.size());
        assertEquals(getDevelopers(), listDevelopers);
    }
    
    @Test
    public void deleteDeveloperByIdTest(){
        int idDeveloper = 3;
        
        doNothing().when(repository).deleteId(idDeveloper);
        
        service.deleteDeveloperById(idDeveloper);
        
        verify(repository).deleteId(idDeveloper);
    }
    
    private List<Skill> getSkills() {
        return List.of(new Skill("Test 1"), new Skill("Test 2"), new Skill("Test 3"));
    }
    
    private List<Developer> getDevelopers() {
        return List.of(new Developer("Test1", "Test1", getSkills()), 
                       new Developer("Test2", "Test2", getSkills()),
                       new Developer("Test3", "Test3", getSkills()));
    }
    
    private Developer getDeveloper() {
        return new Developer("Test1", "Test1", getSkills());
    }    
}
