package com.abigdreamer.saasovation.agilepm.domain.model.team;

import java.util.Date;

import com.abigdreamer.saasovation.agilepm.domain.model.DomainRegistry;
import com.abigdreamer.saasovation.agilepm.domain.model.team.TeamMember;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 团队成员测试
 * 
 * @author Darkness
 * @date 2014-5-30 上午9:18:07
 * @version V1.0
 */
public class TeamMemberTest extends TeamCommonTest {

    public TeamMemberTest() {
        super();
    }

    public void testCreate() throws Exception {
        TeamMember teamMember =
                new TeamMember(
                        new TenantId("T-12345"),
                        "bill",
                        "Bill",
                        "Smith",
                        "bill@saasovation.com",
                        new Date());

        assertNotNull(teamMember);

        DomainRegistry.teamMemberRepository().save(teamMember);

        assertEquals("bill", teamMember.username());
        assertEquals("Bill", teamMember.firstName());
        assertEquals("Smith", teamMember.lastName());
        assertEquals("bill@saasovation.com", teamMember.emailAddress());
        assertEquals(teamMember.username(), teamMember.teamMemberId().id());
    }

    public void testChangeEmailAddress() throws Exception {
        TeamMember teamMember = this.teamMemberForTest();

        assertFalse(teamMember.emailAddress().equals("billsmith@saasovation.com"));

        // later...
        Date notificationOccurredOn = new Date();

        teamMember.changeEmailAddress("billsmith@saasovation.com", notificationOccurredOn);

        assertEquals("billsmith@saasovation.com", teamMember.emailAddress());
    }

    public void testChangeName() throws Exception {
        TeamMember teamMember = this.teamMemberForTest();

        assertFalse(teamMember.lastName().equals("Gates"));

        // later...
        Date notificationOccurredOn = new Date();

        teamMember.changeName("Bill", "Gates", notificationOccurredOn);

        assertEquals("Bill", teamMember.firstName());
        assertEquals("Gates", teamMember.lastName());
    }

    public void testDisable() throws Exception {
        TeamMember teamMember = this.teamMemberForTest();

        assertTrue(teamMember.isEnabled());

        // later...
        Date notificationOccurredOn = new Date();

        teamMember.disable(notificationOccurredOn);

        assertFalse(teamMember.isEnabled());
    }

    public void testEnable() throws Exception {
        TeamMember teamMember = this.teamMemberForTest();

        teamMember.disable(this.twoHoursEarlierThanNow());

        assertFalse(teamMember.isEnabled());

        // later...
        Date notificationOccurredOn = new Date();

        teamMember.enable(notificationOccurredOn);

        assertTrue(teamMember.isEnabled());
    }

    public void testDisallowEarlierDisabling() {
        TeamMember teamMember = this.teamMemberForTest();

        teamMember.disable(this.twoHoursEarlierThanNow());

        assertFalse(teamMember.isEnabled());

        // later...
        Date notificationOccurredOn = new Date();

        teamMember.enable(notificationOccurredOn);

        assertTrue(teamMember.isEnabled());

        // latent notification...
        teamMember.disable(this.twoMinutesEarlierThanNow());

        assertTrue(teamMember.isEnabled());
    }

    public void testDisallowEarlierEnabling() {
        TeamMember teamMember = this.teamMemberForTest();

        assertTrue(teamMember.isEnabled());

        // later...
        Date notificationOccurredOn = new Date();

        teamMember.disable(notificationOccurredOn);

        assertFalse(teamMember.isEnabled());

        // latent notification...
        teamMember.enable(this.twoMinutesEarlierThanNow());

        assertFalse(teamMember.isEnabled());
    }
}
