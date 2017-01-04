package com.saasovation.collaboration.application.forum;

import com.saasovation.collaboration.application.ApplicationTest;
import com.saasovation.collaboration.application.forum.data.DiscussionCommandResult;
import com.saasovation.collaboration.domain.model.DomainRegistry;
import com.saasovation.collaboration.domain.model.forum.Discussion;
import com.saasovation.collaboration.domain.model.forum.Forum;
import com.saasovation.collaboration.domain.model.forum.Post;
import com.saasovation.collaboration.domain.model.forum.PostId;

/**
 * 讨论应用服务测试
 * 
 * @author Darkness
 * @date 2014-7-1 上午11:06:44 
 * @version V1.0
 */
public class DiscussionApplicationServiceTest extends ApplicationTest {

    private String discussionId;
    private String inReplyToPostId;
    private String postId;

    public DiscussionApplicationServiceTest() {
        super();
    }

    public void testCloseDiscussion() throws Exception {

        Forum forum = this.forumAggregate();

        DomainRegistry.forumRepository().save(forum);

        Discussion discussion = this.discussionAggregate(forum);

        assertFalse(discussion.isClosed());

        DomainRegistry.discussionRepository().save(discussion);

        discussionApplicationService
            .closeDiscussion(
                    discussion.tenant().id(),
                    discussion.discussionId().id());

        Discussion closedDiscussion =
                DomainRegistry
                    .discussionRepository()
                    .discussionOfId(
                            discussion.tenant(),
                            discussion.discussionId());

        assertNotNull(closedDiscussion);
        assertTrue(closedDiscussion.isClosed());
    }

    public void testPostToDiscussion() throws Exception {

        Forum forum = this.forumAggregate();

        DomainRegistry.forumRepository().save(forum);

        Discussion discussion = this.discussionAggregate(forum);

        DomainRegistry.discussionRepository().save(discussion);

        DiscussionCommandResult result = new DiscussionCommandResult() {
            @Override
            public void resultingDiscussionId(String aDiscussionId) {
                discussionId = aDiscussionId;
            }
            @Override
            public void resultingPostId(String aPostId) {
                postId = aPostId;
            }
            @Override
            public void resultingInReplyToPostId(String aReplyToPostId) {
                throw new UnsupportedOperationException("Should not be reached.");
            }
        };

        discussionApplicationService
            .postToDiscussion(
                    discussion.tenant().id(),
                    discussion.discussionId().id(),
                    "authorId1",
                    "Post Test",
                    "Post test text...",
                    result);

        Post post =
                DomainRegistry
                    .postRepository()
                    .postOfId(
                            discussion.tenant(),
                            new PostId(postId));

        assertNotNull(discussionId);
        assertNotNull(post);
        assertEquals("authorId1", post.author().identity());
        assertEquals("Post Test", post.subject());
        assertEquals("Post test text...", post.bodyText());
    }

    public void testPostToDiscussionInReplyTo() throws Exception {

        Forum forum = this.forumAggregate();

        DomainRegistry.forumRepository().save(forum);

        Discussion discussion = this.discussionAggregate(forum);

        DomainRegistry.discussionRepository().save(discussion);

        DiscussionCommandResult result = new DiscussionCommandResult() {
            @Override
            public void resultingDiscussionId(String aDiscussionId) {
                discussionId = aDiscussionId;
            }
            @Override
            public void resultingPostId(String aPostId) {
                postId = aPostId;
            }
            @Override
            public void resultingInReplyToPostId(String aReplyToPostId) {
                inReplyToPostId = aReplyToPostId;
            }
        };

        discussionApplicationService
            .postToDiscussion(
                    discussion.tenant().id(),
                    discussion.discussionId().id(),
                    "authorId1",
                    "Post Test",
                    "Post test text...",
                    result);

        discussionApplicationService
            .postToDiscussionInReplyTo(
                discussion.tenant().id(),
                discussion.discussionId().id(),
                postId,
                "authorId2",
                "Post In Reply To Test",
                "Post test text in reply to...",
                result);

        Post postedInReplyTo =
                DomainRegistry
                    .postRepository()
                    .postOfId(
                            discussion.tenant(),
                            new PostId(postId));

        assertNotNull(discussionId);
        assertNotNull(inReplyToPostId);
        assertNotNull(postedInReplyTo);
        assertEquals("authorId2", postedInReplyTo.author().identity());
        assertEquals("Post In Reply To Test", postedInReplyTo.subject());
        assertEquals("Post test text in reply to...", postedInReplyTo.bodyText());
    }

    public void testReopenDiscussion() throws Exception {

        Forum forum = this.forumAggregate();

        DomainRegistry.forumRepository().save(forum);

        Discussion discussion = this.discussionAggregate(forum);

        discussion.close();

        assertTrue(discussion.isClosed());

        DomainRegistry.discussionRepository().save(discussion);

        discussionApplicationService
            .reopenDiscussion(
                    discussion.tenant().id(),
                    discussion.discussionId().id());

        Discussion openDiscussion =
                DomainRegistry
                    .discussionRepository()
                    .discussionOfId(
                            discussion.tenant(),
                            discussion.discussionId());

        assertNotNull(openDiscussion);
        assertFalse(openDiscussion.isClosed());
    }
}
