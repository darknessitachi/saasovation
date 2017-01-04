package com.abigdreamer.saasovation.agilepm.domain.model.team;

import java.util.Date;


import com.abigdreamer.saasovation.agilepm.domain.model.DomainTest;
import com.abigdreamer.saasovation.agilepm.domain.model.team.ProductOwner;
import com.abigdreamer.saasovation.agilepm.domain.model.team.Team;
import com.abigdreamer.saasovation.agilepm.domain.model.team.TeamMember;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 团队测试基类
 * 
 * @author Darkness
 * @date 2014-5-30 上午9:17:36
 * @version V1.0
 */
public abstract class TeamCommonTest extends DomainTest {

    public TeamCommonTest() {
        super();
    }

    protected Date twoHoursEarlierThanNow() {
        return new Date(new Date().getTime() - (3600000 * 2));
    }

    protected Date twoMinutesEarlierThanNow() {
        return new Date(new Date().getTime() - (1000 * 120));
    }

    protected ProductOwner productOwnerForTest() {
        ProductOwner productOwner =
                new ProductOwner(
                        new TenantId("T-12345"),
                        "zoe",
                        "Zoe",
                        "Doe",
                        "zoe@saasovation.com",
                        new Date(new Date().getTime() - (86400000L * 30)));

        return productOwner;
    }

    protected Team teamForTest() {
        TenantId tenantId = new TenantId("T-12345");

        Team team = new Team(tenantId, "Identity and Access Management");

        return team;
    }

    protected TeamMember teamMemberForTest() {
        return this.teamMemberForTest1();
    }

    protected TeamMember teamMemberForTest1() {
        TeamMember teamMember =
                new TeamMember(
                        new TenantId("T-12345"),
                        "bill",
                        "Bill",
                        "Smith",
                        "bill@saasovation.com",
                        new Date(new Date().getTime() - (86400000L * 30)));

        return teamMember;
    }

    protected TeamMember teamMemberForTest2() {
        TeamMember teamMember =
                new TeamMember(
                        new TenantId("T-12345"),
                        "zoe",
                        "Zoe",
                        "Doe",
                        "zoe@saasovation.com",
                        new Date(new Date().getTime() - (86400000L * 30)));

        return teamMember;
    }

    protected TeamMember teamMemberForTest3() {
        TeamMember teamMember =
                new TeamMember(
                        new TenantId("T-12345"),
                        "jdoe",
                        "John",
                        "Doe",
                        "jdoe@saasovation.com",
                        new Date(new Date().getTime() - (86400000L * 30)));

        return teamMember;
    }
}
