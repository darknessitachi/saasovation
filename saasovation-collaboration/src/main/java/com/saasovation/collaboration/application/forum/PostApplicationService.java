package com.saasovation.collaboration.application.forum;

import com.saasovation.collaboration.domain.model.collaborator.CollaboratorService;
import com.saasovation.collaboration.domain.model.collaborator.Moderator;
import com.saasovation.collaboration.domain.model.forum.Forum;
import com.saasovation.collaboration.domain.model.forum.ForumId;
import com.saasovation.collaboration.domain.model.forum.ForumRepository;
import com.saasovation.collaboration.domain.model.forum.Post;
import com.saasovation.collaboration.domain.model.forum.PostId;
import com.saasovation.collaboration.domain.model.forum.PostRepository;
import com.saasovation.collaboration.domain.model.tenant.Tenant;

/**
 * 帖子应用服务
 * 
 * @author Darkness
 * @date 2014-5-31 下午2:39:13
 * @version V1.0
 */
public class PostApplicationService {

    private CollaboratorService collaboratorService;
    private ForumRepository forumRepository;
    private PostRepository postRepository;

    public PostApplicationService(
            PostRepository aPostRepository,
            ForumRepository aForumRepository,
            CollaboratorService aCollaboratorService) {

        super();

        this.collaboratorService = aCollaboratorService;
        this.forumRepository = aForumRepository;
        this.postRepository = aPostRepository;
    }

    /**
     * 参与者提交
     * @param aTenantId
     * @param aForumId
     * @param aPostId
     * @param aModeratorId
     * @param aSubject
     * @param aBodyText
     */
    public void moderatePost(
            String aTenantId,
            String aForumId,
            String aPostId,
            String aModeratorId,
            String aSubject,
            String aBodyText) {

        Tenant tenant = new Tenant(aTenantId);

        Forum forum =
                this.forumRepository()
                    .forumOfId(
                            tenant,
                            new ForumId(aForumId));

        Moderator moderator =
                this.collaboratorService().moderatorFrom(tenant, aModeratorId);

        Post post = this.postRepository().postOfId(tenant, new PostId(aPostId));

        forum.moderatePost(post, moderator, aSubject, aBodyText);

        this.postRepository().save(post);
    }

    private CollaboratorService collaboratorService() {
        return this.collaboratorService;
    }

    private ForumRepository forumRepository() {
        return this.forumRepository;
    }

    private PostRepository postRepository() {
        return this.postRepository;
    }
}
