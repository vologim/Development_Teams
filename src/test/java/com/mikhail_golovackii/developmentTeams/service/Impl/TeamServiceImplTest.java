package com.mikhail_golovackii.developmentTeams.service.Impl;

import com.mikhail_golovackii.developmentTeams.model.Developer;
import com.mikhail_golovackii.developmentTeams.model.Team;
import com.mikhail_golovackii.developmentTeams.repository.impl.TeamRepositoryImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class TeamServiceImplTest {

    private List<Developer> developers;
    private Developer dev1;
    private Developer dev2;
    private Developer dev3;

    private List<Team> teams;
    private Team team1;
    private Team team2;
    private Team team3;

    public TeamServiceImplTest() {
        developers = new ArrayList<>();
        dev1 = new Developer();
        dev2 = new Developer();
        dev3 = new Developer();
        developers.add(dev1);
        developers.add(dev2);
        developers.add(dev3);

        teams = new ArrayList<>();
        team1 = new Team("Test 1", developers);
        team2 = new Team("Test 2", developers);
        team3 = new Team("Test 3", developers);
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);
    }

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
        doNothing().when(repository).save(team1);
        service.saveTeam(team1);

        verify(repository).save(team1);
    }

    @Test
    public void updateTeamTest() {
        when(repository.update(1, team1)).thenReturn(team1);

        Team testTeam = service.updateTeam(1, team1);

        assertSame(team1, testTeam);
        assertNotNull(testTeam);
        assertEquals(developers, testTeam.getDevelopers());
        assertEquals(3, testTeam.getDevelopers().size());
    }

    @Test
    public void getTeamByIdTest() {
        team1.setId(1);
        when(repository.getId(1)).thenReturn(team1);

        Team testTeam = service.getTeamById(1);

        assertSame(team1, testTeam);
        assertEquals(1, testTeam.getId());
        assertNotNull(testTeam);
    }

    @Test
    public void getAllTeamsTest() {
        when(repository.getAll()).thenReturn(teams);

        List<Team> testTeams = service.getAllTeams();

        assertEquals(3, testTeams.size());
        assertSame(teams, testTeams);
    }

    @Test
    public void deleteTeam() {
        int idTeam = 1;

        doNothing().when(repository).deleteId(idTeam);

        service.deleteTeamById(idTeam);

        verify(repository).deleteId(idTeam);
    }
}
