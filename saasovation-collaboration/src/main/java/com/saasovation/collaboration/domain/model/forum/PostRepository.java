package com.saasovation.collaboration.domain.model.forum;

import com.saasovation.collaboration.domain.model.tenant.Tenant;

/**
 * 提交仓储
 * 
 * @author Darkness
 * @date 2014-5-30 下午4:01:12
 * @version V1.0
 */
public interface PostRepository {

	PostId nextIdentity();

	Post postOfId(Tenant aTenant, PostId aPostId);

	void save(Post aPost);
}
