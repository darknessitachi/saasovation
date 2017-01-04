package com.saasovation.collaboration.domain.model.collaborator;

import com.saasovation.collaboration.domain.model.tenant.Tenant;

/**
 * 参与者服务
 * 
 * @author Darkness
 * @date 2014-5-31 下午1:46:41
 * @version V1.0
 */
public interface CollaboratorService {

	Author authorFrom(Tenant aTenant, String anIdentity);

	Creator creatorFrom(Tenant aTenant, String anIdentity);

	Moderator moderatorFrom(Tenant aTenant, String anIdentity);

	Owner ownerFrom(Tenant aTenant, String anIdentity);

	Participant participantFrom(Tenant aTenant, String anIdentity);
}
