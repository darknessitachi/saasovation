package com.saasovation.collaboration.application.forum;

import com.saasovation.collaboration.application.forum.data.DiscussionCommandResult;
import com.saasovation.collaboration.domain.model.collaborator.Author;
import com.saasovation.collaboration.domain.model.collaborator.CollaboratorService;
import com.saasovation.collaboration.domain.model.forum.Discussion;
import com.saasovation.collaboration.domain.model.forum.DiscussionId;
import com.saasovation.collaboration.domain.model.forum.DiscussionRepository;
import com.saasovation.collaboration.domain.model.forum.ForumIdentityService;
import com.saasovation.collaboration.domain.model.forum.Post;
import com.saasovation.collaboration.domain.model.forum.PostId;
import com.saasovation.collaboration.domain.model.forum.PostRepository;
import com.saasovation.collaboration.domain.model.tenant.Tenant;

/**
 * 讨论应用服务
 * 
 * @author Darkness
 * @date 2014-5-31 下午1:46:25
 * @version V1.0
 */
public class DiscussionApplicationService {

    private CollaboratorService collaboratorService;
    private DiscussionRepository discussionRepository;
    private ForumIdentityService forumIdentityService;
    private PostRepository postRepository;

    public DiscussionApplicationService(
            DiscussionRepository aDiscussionRepository,
            ForumIdentityService aForumIdentityService,
            PostRepository aPostRepository,
            CollaboratorService aCollaboratorService) {

        super();

        this.collaboratorService = aCollaboratorService;
        this.discussionRepository = aDiscussionRepository;
        this.forumIdentityService = aForumIdentityService;
        this.postRepository = aPostRepository;
    }

    /**
     * 关闭讨论
     * @param aTenantId
     * @param aDiscussionId
     */
    public void closeDiscussion(String aTenantId, String aDiscussionId) {
        Discussion discussion = queryDiscussion(aTenantId, aDiscussionId);

        discussion.close();

        this.discussionRepository().save(discussion);
    }

    /**
     * 提交到讨论
     * @param aTenantId
     * @param aDiscussionId
     * @param anAuthorId
     * @param aSubject
     * @param aBodyText
     * @param aDiscussionCommandResult
     */
    public void postToDiscussion(
            String aTenantId,
            String aDiscussionId,
            String anAuthorId,
            String aSubject,
            String aBodyText,
            DiscussionCommandResult aDiscussionCommandResult) {

        Discussion discussion = queryDiscussion(aTenantId, aDiscussionId);

        Author author = queryAuthor(aTenantId, anAuthorId);

        Post post = discussion.post(this.forumIdentityService(), author, aSubject, aBodyText);

        this.postRepository().save(post);

        aDiscussionCommandResult.resultingDiscussionId(aDiscussionId);
        aDiscussionCommandResult.resultingPostId(post.postId().id());
    }

    /**
     * 回复
     * @param aTenantId
     * @param aDiscussionId
     * @param aReplyToPostId
     * @param anAuthorId
     * @param aSubject
     * @param aBodyText
     * @param aDiscussionCommandResult
     */
    public void postToDiscussionInReplyTo(
            String aTenantId,
            String aDiscussionId,
            String aReplyToPostId,
            String anAuthorId,
            String aSubject,
            String aBodyText,
            DiscussionCommandResult aDiscussionCommandResult) {

        Discussion discussion = queryDiscussion(aTenantId, aDiscussionId);

        Author author = queryAuthor(aTenantId, anAuthorId);

        Post post = discussion.post(
                        this.forumIdentityService(),
                        new PostId(aReplyToPostId),
                        author,
                        aSubject,
                        aBodyText);

        this.postRepository().save(post);

        aDiscussionCommandResult.resultingDiscussionId(aDiscussionId);
        aDiscussionCommandResult.resultingPostId(post.postId().id());
        aDiscussionCommandResult.resultingInReplyToPostId(aReplyToPostId);
    }

    /**
     * 重新打开讨论
     * @param aTenantId
     * @param aDiscussionId
     */
    public void reopenDiscussion(String aTenantId, String aDiscussionId) {
        Discussion discussion = queryDiscussion(aTenantId, aDiscussionId);

        discussion.reopen();

        this.discussionRepository().save(discussion);
    }

    /**
     * 查询作者
     * @param aTenantId
     * @param anAuthorId
     * @return
     */
    private Author queryAuthor(String aTenantId, String anAuthorId) {
    	return this.collaboratorService().authorFrom(new Tenant(aTenantId), anAuthorId);
    }
    
    /**
     * 查询讨论
     * @param aTenantId
     * @param aDiscussionId
     * @return
     */
    private Discussion queryDiscussion(String aTenantId, String aDiscussionId) {
    	return this.discussionRepository()
                .discussionOfId(new Tenant(aTenantId),
                        new DiscussionId(aDiscussionId));
    }
    
    private CollaboratorService collaboratorService() {
        return this.collaboratorService;
    }

    private DiscussionRepository discussionRepository() {
        return this.discussionRepository;
    }

    private ForumIdentityService forumIdentityService() {
        return this.forumIdentityService;
    }

    private PostRepository postRepository() {
        return this.postRepository;
    }
}
