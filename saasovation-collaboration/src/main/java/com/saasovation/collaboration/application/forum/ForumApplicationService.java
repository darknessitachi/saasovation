package com.saasovation.collaboration.application.forum;

import com.saasovation.collaboration.application.forum.data.ForumCommandResult;
import com.saasovation.collaboration.domain.model.collaborator.Author;
import com.saasovation.collaboration.domain.model.collaborator.CollaboratorService;
import com.saasovation.collaboration.domain.model.collaborator.Creator;
import com.saasovation.collaboration.domain.model.collaborator.Moderator;
import com.saasovation.collaboration.domain.model.forum.Discussion;
import com.saasovation.collaboration.domain.model.forum.DiscussionId;
import com.saasovation.collaboration.domain.model.forum.DiscussionRepository;
import com.saasovation.collaboration.domain.model.forum.Forum;
import com.saasovation.collaboration.domain.model.forum.ForumId;
import com.saasovation.collaboration.domain.model.forum.ForumIdentityService;
import com.saasovation.collaboration.domain.model.forum.ForumRepository;
import com.saasovation.collaboration.domain.model.tenant.Tenant;

/**
 * 论坛应用服务
 * 
 * @author Darkness
 * @date 2014-5-31 下午2:22:05
 * @version V1.0
 */
public class ForumApplicationService {

    private CollaboratorService collaboratorService;
    private DiscussionQueryService discussionQueryService;
    private DiscussionRepository discussionRepository;
    private ForumIdentityService forumIdentityService;
    private ForumQueryService forumQueryService;
    private ForumRepository forumRepository;

    public ForumApplicationService(
            ForumQueryService aForumQueryService,
            ForumRepository aForumRepository,
            ForumIdentityService aForumIdentityService,
            DiscussionQueryService aDiscussionQueryService,
            DiscussionRepository aDiscussionRepository,
            CollaboratorService aCollaboratorService) {

        super();

        this.collaboratorService = aCollaboratorService;
        this.discussionQueryService = aDiscussionQueryService;
        this.discussionRepository = aDiscussionRepository;
        this.forumIdentityService = aForumIdentityService;
        this.forumQueryService = aForumQueryService;
        this.forumRepository = aForumRepository;
    }

    /**
     * 分配论坛版主
     * @param aTenantId
     * @param aForumId
     * @param aModeratorId
     */
    public void assignModeratorToForum(
            String aTenantId,
            String aForumId,
            String aModeratorId) {

        Tenant tenant = new Tenant(aTenantId);

        Forum forum =
                this.forumRepository()
                    .forumOfId(
                            tenant,
                            new ForumId(aForumId));

        Moderator moderator =
                this.collaboratorService().moderatorFrom(tenant, aModeratorId);

        forum.assignModerator(moderator);

        this.forumRepository().save(forum);
    }

    /**
     * 修改论坛描述
     * @param aTenantId
     * @param aForumId
     * @param aDescription
     */
    public void changeForumDescription(
            String aTenantId,
            String aForumId,
            String aDescription) {

        Tenant tenant = new Tenant(aTenantId);

        Forum forum =
                this.forumRepository()
                    .forumOfId(
                            tenant,
                            new ForumId(aForumId));

        forum.changeDescription(aDescription);

        this.forumRepository().save(forum);
    }

    /**
     * 修改论坛主题
     * @param aTenantId
     * @param aForumId
     * @param aSubject
     */
    public void changeForumSubject(
            String aTenantId,
            String aForumId,
            String aSubject) {

        Tenant tenant = new Tenant(aTenantId);

        Forum forum =
                this.forumRepository()
                    .forumOfId(
                            tenant,
                            new ForumId(aForumId));

        forum.changeSubject(aSubject);

        this.forumRepository().save(forum);
    }

    /**
     * 关闭论坛
     * @param aTenantId
     * @param aForumId
     */
    public void closeForum(
            String aTenantId,
            String aForumId) {

        Tenant tenant = new Tenant(aTenantId);

        Forum forum =
                this.forumRepository()
                    .forumOfId(
                            tenant,
                            new ForumId(aForumId));

        forum.close();

        this.forumRepository().save(forum);
    }

    /**
     * 重新打开论坛
     * @param aTenantId
     * @param aForumId
     */
    public void reopenForum(
            String aTenantId,
            String aForumId) {

        Tenant tenant = new Tenant(aTenantId);

        Forum forum =
                this.forumRepository()
                    .forumOfId(
                            tenant,
                            new ForumId(aForumId));

        forum.reopen();

        this.forumRepository().save(forum);
    }

    /**
     * 开启论坛
     * @param aTenantId
     * @param aCreatorId
     * @param aModeratorId
     * @param aSubject
     * @param aDescription
     * @param aResult
     */
    public void startForum(
            String aTenantId,
            String aCreatorId,
            String aModeratorId,
            String aSubject,
            String aDescription,
            ForumCommandResult aResult) {

        Forum forum =
                this.startNewForum(
                    new Tenant(aTenantId),
                    aCreatorId,
                    aModeratorId,
                    aSubject,
                    aDescription,
                    null);

        if (aResult != null) {
            aResult.resultingForumId(forum.forumId().id());
        }
    }

    public void startExclusiveForum(
            String aTenantId,
            String anExclusiveOwner,
            String aCreatorId,
            String aModeratorId,
            String aSubject,
            String aDescription,
            ForumCommandResult aResult) {

        Tenant tenant = new Tenant(aTenantId);

        String forumId =
                this.forumQueryService()
                    .forumIdOfExclusiveOwner(
                            aTenantId,
                            anExclusiveOwner);

        Forum forum = null;

        if (forumId != null) {
            forum = this.forumRepository()
                        .forumOfId(
                                tenant,
                                new ForumId(forumId));
        }

        if (forum == null) {
            forum =
                    this.startNewForum(
                        tenant,
                        aCreatorId,
                        aModeratorId,
                        aSubject,
                        aDescription,
                        anExclusiveOwner);
        }

        if (aResult != null) {
            aResult.resultingForumId(forum.forumId().id());
        }
    }

    public void startExclusiveForumWithDiscussion(
            String aTenantId,
            String anExclusiveOwner,
            String aCreatorId,
            String aModeratorId,
            String anAuthorId,
            String aForumSubject,
            String aForumDescription,
            String aDiscussionSubject,
            ForumCommandResult aResult) {

        Tenant tenant = new Tenant(aTenantId);

        String forumId =
                this.forumQueryService()
                    .forumIdOfExclusiveOwner(
                            aTenantId,
                            anExclusiveOwner);

        Forum forum = null;

        if (forumId != null) {
            forum = this.forumRepository()
                        .forumOfId(
                                tenant,
                                new ForumId(forumId));
        }

        if (forum == null) {
            forum = this.startNewForum(
                    tenant,
                    aCreatorId,
                    aModeratorId,
                    aForumSubject,
                    aForumDescription,
                    anExclusiveOwner);
        }

        String discussionId =
                this.discussionQueryService()
                    .discussionIdOfExclusiveOwner(
                            aTenantId,
                            anExclusiveOwner);

        Discussion discussion = null;

        if (discussionId != null) {
            discussion = this.discussionRepository()
                             .discussionOfId(
                                     tenant,
                                     new DiscussionId(discussionId));
        }

        if (discussion == null) {
            Author author =
                    this.collaboratorService().authorFrom(tenant, anAuthorId);

            discussion =
                    forum.startDiscussionFor(
                            this.forumIdentityService(),
                            author,
                            aDiscussionSubject,
                            anExclusiveOwner);

            this.discussionRepository().save(discussion);
        }

        if (aResult != null) {
            aResult.resultingForumId(forum.forumId().id());
            aResult.resultingDiscussionId(discussion.discussionId().id());
        }
    }

    private CollaboratorService collaboratorService() {
        return this.collaboratorService;
    }

    private DiscussionQueryService discussionQueryService() {
        return this.discussionQueryService;
    }

    private DiscussionRepository discussionRepository() {
        return this.discussionRepository;
    }

    private ForumIdentityService forumIdentityService() {
        return this.forumIdentityService;
    }

    private ForumQueryService forumQueryService() {
        return this.forumQueryService;
    }

    private ForumRepository forumRepository() {
        return this.forumRepository;
    }

    /**
     * 开启新的论坛
     * @param aTenant
     * @param aCreatorId
     * @param aModeratorId
     * @param aSubject
     * @param aDescription
     * @param anExclusiveOwner
     * @return
     */
    private Forum startNewForum(
            Tenant aTenant,
            String aCreatorId,
            String aModeratorId,
            String aSubject,
            String aDescription,
            String anExclusiveOwner) {

        Creator creator =
                this.collaboratorService().creatorFrom(aTenant, aCreatorId);

        Moderator moderator =
                this.collaboratorService().moderatorFrom(aTenant, aModeratorId);

        Forum newForum =
            new Forum(
                    aTenant,
                    this.forumRepository().nextIdentity(),
                    creator,
                    moderator,
                    aSubject,
                    aDescription,
                    anExclusiveOwner);

        this.forumRepository().save(newForum);

        return newForum;
    }
}
