package com.abigdreamer.saasovation.agilepm.domain.model.team;

import java.util.Collection;

import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


public interface TeamMemberRepository {

	Collection<TeamMember> allTeamMembersOfTenant(TenantId aTenantId);

	void remove(TeamMember aTeamMember);

	void removeAll(Collection<TeamMember> aTeamMemberCollection);

	void save(TeamMember aTeamMember);

	void saveAll(Collection<TeamMember> aTeamMemberCollection);

	TeamMember teamMemberOfIdentity(TenantId aTenantId, String aUsername);
}
