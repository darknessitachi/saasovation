package com.saasovation.collaboration.domain.model.forum;

import com.saasovation.collaboration.domain.model.tenant.Tenant;

/**
 * 讨论仓储
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:41:49
 * @version V1.0
 */
public interface DiscussionRepository {

	Discussion discussionOfId(Tenant aTenantId, DiscussionId aDiscussionId);

	DiscussionId nextIdentity();

	void save(Discussion aDiscussion);
}
