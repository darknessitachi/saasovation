package com.saasovation.collaboration.domain.model.forum;

/**
 * 论坛身份认证服务
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:43:34
 * @version V1.0
 */
public class ForumIdentityService {

    private DiscussionRepository discussionRepository;
    private ForumRepository forumRepository;
    private PostRepository postRepository;

    public ForumIdentityService(
            ForumRepository aForumRepository,
            DiscussionRepository aDiscussionRepository,
            PostRepository aPostRepository) {

        super();

        this.discussionRepository = aDiscussionRepository;
        this.forumRepository = aForumRepository;
        this.postRepository = aPostRepository;
    }

    public DiscussionId nextDiscussionId() {
        return this.discussionRepository().nextIdentity();
    }

    public ForumId nextForumId() {
        return this.forumRepository().nextIdentity();
    }

    public PostId nextPostId() {
        return this.postRepository().nextIdentity();
    }

    private DiscussionRepository discussionRepository() {
        return this.discussionRepository;
    }

    private ForumRepository forumRepository() {
        return this.forumRepository;
    }

    private PostRepository postRepository() {
        return this.postRepository;
    }
}
