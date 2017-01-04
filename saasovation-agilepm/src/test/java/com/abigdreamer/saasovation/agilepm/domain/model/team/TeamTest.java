package com.abigdreamer.saasovation.agilepm.domain.model.team;

import com.abigdreamer.saasovation.agilepm.domain.model.DomainRegistry;
import com.abigdreamer.saasovation.agilepm.domain.model.team.ProductOwner;
import com.abigdreamer.saasovation.agilepm.domain.model.team.Team;
import com.abigdreamer.saasovation.agilepm.domain.model.team.TeamMember;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;

/**
 * 团队测试
 * 
 * @author Darkness
 * @date 2014-5-30 上午9:18:55
 * @version V1.0
 */
public class TeamTest extends TeamCommonTest {

    public void testCreate() throws Exception {
        TenantId tenantId = new TenantId("T-12345");

        Team team = new Team(tenantId, "Identity and Access Management");

        DomainRegistry.teamRepository().save(team);

        assertEquals("Identity and Access Management", team.name());
    }

    public void testAssignProductOwner() throws Exception {
        Team team = this.teamForTest();

        ProductOwner productOwner = this.productOwnerForTest();

        team.assignProductOwner(productOwner);

        assertNotNull(team.productOwner());
        assertEquals(productOwner.productOwnerId(), team.productOwner().productOwnerId());
    }

    public void testAssignTeamMembers() throws Exception {
        Team team = this.teamForTest();

        TeamMember teamMember1 = this.teamMemberForTest1();
        TeamMember teamMember2 = this.teamMemberForTest2();
        TeamMember teamMember3 = this.teamMemberForTest3();

        team.assignTeamMember(teamMember1);
        team.assignTeamMember(teamMember2);
        team.assignTeamMember(teamMember3);

        assertFalse(team.allTeamMembers().isEmpty());
        assertEquals(3, team.allTeamMembers().size());

        assertTrue(team.isTeamMember(teamMember1));
        assertTrue(team.isTeamMember(teamMember2));
        assertTrue(team.isTeamMember(teamMember3));
    }

    public void testRemoveTeamMembers() throws Exception {
        Team team = this.teamForTest();

        TeamMember teamMember1 = this.teamMemberForTest1();
        TeamMember teamMember2 = this.teamMemberForTest2();
        TeamMember teamMember3 = this.teamMemberForTest3();

        team.assignTeamMember(teamMember1);
        team.assignTeamMember(teamMember2);
        team.assignTeamMember(teamMember3);

        assertFalse(team.allTeamMembers().isEmpty());
        assertEquals(3, team.allTeamMembers().size());

        team.removeTeamMember(teamMember2);

        assertFalse(team.allTeamMembers().isEmpty());
        assertEquals(2, team.allTeamMembers().size());

        assertTrue(team.isTeamMember(teamMember1));
        assertFalse(team.isTeamMember(teamMember2));
        assertTrue(team.isTeamMember(teamMember3));
    }
}
