package com.saasovation.collaboration.domain.model.forum;

import com.saasovation.collaboration.domain.model.tenant.Tenant;

/**
 * 论坛仓储
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:44:41
 * @version V1.0
 */
public interface ForumRepository {

	Forum forumOfId(Tenant aTenant, ForumId aForumId);

	ForumId nextIdentity();

	void save(Forum aForum);
}
