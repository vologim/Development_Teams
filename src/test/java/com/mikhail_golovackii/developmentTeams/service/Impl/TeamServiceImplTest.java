package com.mikhail_golovackii.developmentTeams.service.Impl;

import com.mikhail_golovackii.developmentTeams.model.Developer;
import com.mikhail_golovackii.developmentTeams.model.Team;
import com.mikhail_golovackii.developmentTeams.repository.impl.TeamRepositoryImpl;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class TeamServiceImplTest {

    @Mock
    private TeamRepositoryImpl repository;

    @InjectMocks
    private TeamServiceImpl service;

    @Before
    public void initBefore() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTeamTest() {
        doNothing().when(repository).save(getTeam());
        service.saveTeam(getTeam());

        verify(repository).save(getTeam());
    }

    @Test
    public void updateTeamTest() {
        when(repository.update(1, getTeam())).thenReturn(getTeam());

        Team testTeam = service.updateTeam(1, getTeam());

        assertEquals(getTeam(), testTeam);
        assertNotNull(testTeam);
        assertEquals(getDevelopers(), testTeam.getDevelopers());
        assertEquals(3, testTeam.getDevelopers().size());
    }

    @Test
    public void getTeamByIdTest() {
        Team team = getTeam();
        team.setId(1);
        when(repository.getId(1)).thenReturn(team);

        Team testTeam = service.getTeamById(1);

        assertEquals(team, testTeam);
        assertEquals(1, testTeam.getId());
        assertNotNull(testTeam);
    }

    @Test
    public void getAllTeamsTest() {
        when(repository.getAll()).thenReturn(getTeams());

        List<Team> testTeams = service.getAllTeams();

        assertEquals(3, testTeams.size());
        assertEquals(getTeams(), testTeams);
    }

    @Test
    public void deleteTeam() {
        int idTeam = 1;

        doNothing().when(repository).deleteId(idTeam);

        service.deleteTeamById(idTeam);

        verify(repository).deleteId(idTeam);
    }

    private List<Developer> getDevelopers() {
        return List.of(new Developer(), new Developer(), new Developer());
    }
    
    private List<Team> getTeams() {
        return List.of(new Team("Test1", getDevelopers()), new Team("Test2", getDevelopers()), new Team("Test3", getDevelopers()));
    }
    
    private Team getTeam() {
        return new Team("Test", getDevelopers());
    }
}
