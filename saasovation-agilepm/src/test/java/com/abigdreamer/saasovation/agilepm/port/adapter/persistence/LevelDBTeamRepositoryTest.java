package com.abigdreamer.saasovation.agilepm.port.adapter.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.iq80.leveldb.DB;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.saasovation.agilepm.domain.model.team.Team;
import com.abigdreamer.saasovation.agilepm.domain.model.team.TeamRepository;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;
import com.rapidark.framework.persistence.leveldb.LevelDBProvider;
import com.rapidark.framework.persistence.leveldb.LevelDBUnitOfWork;

import junit.framework.TestCase;


public class LevelDBTeamRepositoryTest extends TestCase {

    private DB database;
    private TeamRepository teamRepository = new LevelDBTeamRepository();

    public LevelDBTeamRepositoryTest() {
        super();
    }
    
    @Override
    protected void setUp() throws Exception {
        DomainEventPublisher.instance().reset();

        this.database = LevelDBProvider.instance().databaseFrom(LevelDBDatabasePath.agilePMPath());

        LevelDBProvider.instance().purge(this.database);

        super.setUp();
    }

    public void testSave() throws Exception {
        Team team = new Team(new TenantId("12345"), "team1");

        LevelDBUnitOfWork.start(this.database);
        teamRepository.save(team);
        LevelDBUnitOfWork.current().commit();

        Team savedTeam = teamRepository.teamNamed(team.tenantId(), team.name());

        assertNotNull(savedTeam);
        assertEquals(team.tenantId(), savedTeam.tenantId());
        assertEquals(team.name(), savedTeam.name());

        Collection<Team> savedTeams =
                this.teamRepository.allTeamsOfTenant(team.tenantId());

        assertFalse(savedTeams.isEmpty());
        assertEquals(1, savedTeams.size());
    }

    public void testRemove() {
        Team team1 = new Team(new TenantId("12345"), "team1");

        Team team2 = new Team(new TenantId("12345"), "team2");

        LevelDBUnitOfWork.start(this.database);
        teamRepository.save(team1);
        teamRepository.save(team2);
        LevelDBUnitOfWork.current().commit();

        LevelDBUnitOfWork.start(this.database);
        teamRepository.remove(team1);
        LevelDBUnitOfWork.current().commit();

        TenantId tenantId = team2.tenantId();

        Collection<Team> savedTeams = teamRepository.allTeamsOfTenant(tenantId);
        assertFalse(savedTeams.isEmpty());
        assertEquals(1, savedTeams.size());
        assertEquals(team2.name(), savedTeams.iterator().next().name());

        LevelDBUnitOfWork.start(this.database);
        teamRepository.remove(team2);
        LevelDBUnitOfWork.current().commit();

        savedTeams = teamRepository.allTeamsOfTenant(tenantId);
        assertTrue(savedTeams.isEmpty());
    }

    public void testSaveAllRemoveAll() throws Exception {
        Team team1 = new Team(new TenantId("12345"), "team1");

        Team team2 = new Team(new TenantId("12345"), "team2");

        Team team3 = new Team(new TenantId("12345"), "team3");

        LevelDBUnitOfWork.start(this.database);
        teamRepository.saveAll(Arrays.asList(new Team[] { team1, team2, team3 }));
        LevelDBUnitOfWork.current().commit();

        TenantId tenantId = team1.tenantId();

        Collection<Team> savedTeams = teamRepository.allTeamsOfTenant(tenantId);
        assertFalse(savedTeams.isEmpty());
        assertEquals(3, savedTeams.size());

        LevelDBUnitOfWork.start(this.database);
        teamRepository.removeAll(Arrays.asList(new Team[] { team1, team3 }));
        LevelDBUnitOfWork.current().commit();

        savedTeams = teamRepository.allTeamsOfTenant(tenantId);
        assertFalse(savedTeams.isEmpty());
        assertEquals(1, savedTeams.size());
        assertEquals(team2.name(), savedTeams.iterator().next().name());

        LevelDBUnitOfWork.start(this.database);
        teamRepository.removeAll(Arrays.asList(new Team[] { team2 }));
        LevelDBUnitOfWork.current().commit();

        savedTeams = teamRepository.allTeamsOfTenant(tenantId);
        assertTrue(savedTeams.isEmpty());
    }

    public void testConcurrentTransactions() throws Exception {
        final List<Integer> orderOfCommits = new ArrayList<Integer>();

        Team team1 = new Team(new TenantId("12345"), "team1");

        LevelDBUnitOfWork.start(database);
        teamRepository.save(team1);

        new Thread() {
           @Override
           public void run() {
               Team team2 = new Team(new TenantId("12345"), "team2");

               LevelDBUnitOfWork.start(database);
               teamRepository.save(team2);
               LevelDBUnitOfWork.current().commit();
               orderOfCommits.add(2);
           }
        }.start();

        Thread.sleep(250L);

        LevelDBUnitOfWork.current().commit();
        orderOfCommits.add(1);

        for (int idx = 0; idx < orderOfCommits.size(); ++idx) {
            assertEquals(idx + 1, orderOfCommits.get(idx).intValue());
        }

        Thread.sleep(250L);

        Collection<Team> savedTeams = teamRepository.allTeamsOfTenant(team1.tenantId());

        assertFalse(savedTeams.isEmpty());
        assertEquals(2, savedTeams.size());
    }

}
