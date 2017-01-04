package com.saasovation.collaboration.application.forum.data;

/**
 * 讨论命令结果
 * 
 * @author Darkness
 * @date 2014-5-31 下午1:52:27
 * @version V1.0
 */
public interface DiscussionCommandResult {

	void resultingDiscussionId(String aDiscussionId);

	void resultingPostId(String aPostId);

	void resultingInReplyToPostId(String aReplyToPostId);
}
