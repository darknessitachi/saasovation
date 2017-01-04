package com.saasovation.collaboration.domain.model.forum;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventSubscriber;

import com.saasovation.collaboration.domain.model.DomainRegistry;
import com.saasovation.collaboration.domain.model.collaborator.Author;
import com.saasovation.collaboration.domain.model.tenant.Tenant;

/**
 * 讨论测试
 * 
 * @author Darkness
 * @date 2014-7-31 下午3:56:33 
 * @version V1.0
 */
public class DiscussionTest extends AbstractForumTest {

    private String bodyText;
    private Discussion discussion;
    private DiscussionId discussionId;
    private Forum forum;
    private ForumId forumId;
    private Post post;
    private Post postAgain;
    private PostId postId;
    private String subject;
    private Tenant tenant;

    public DiscussionTest() {
        super();
    }

    public void testPostToDiscussion() throws Exception {

        forum = this.forumAggregate();

        DomainRegistry.forumRepository().save(forum);

        DomainEventPublisher
            .instance()
            .subscribe(new DomainEventSubscriber<DiscussionStarted>() {
                public void handleEvent(DiscussionStarted aDomainEvent) {
                    discussionId = aDomainEvent.discussionId();
                }
                public Class<DiscussionStarted> subscribedToEventType() {
                    return DiscussionStarted.class;
                }
            });

        discussion = forum.startDiscussion(
                DomainRegistry.forumIdentityService(),
                new Author("jdoe", "John Doe", "jdoe@saasovation.com"),
                "All About DDD");

        DomainRegistry.discussionRepository().save(discussion);

        assertNotNull(discussion);
        assertNotNull(discussionId);

        post = discussion.post(
                DomainRegistry.forumIdentityService(),
                new Author("jdoe", "John Doe", "jdoe@saasovation.com"),
                "All About DDD",
                "I'd like to start a discussion all about doing domain-driven design.");

        DomainRegistry.postRepository().save(post);

        assertNotNull(post);
        assertEquals("jdoe", post.author().identity());
        assertEquals("All About DDD", post.subject());

        expectedEvents(3);
        expectedEvent(ForumStarted.class);
        expectedEvent(DiscussionStarted.class);
        expectedEvent(PostedToDiscussion.class);
    }

    public void testMultiplePostsToDiscussion() throws Exception {

        forum = this.forumAggregate();

        DomainRegistry.forumRepository().save(forum);

        DomainEventPublisher
            .instance()
            .subscribe(new DomainEventSubscriber<DiscussionStarted>() {
                public void handleEvent(DiscussionStarted aDomainEvent) {
                    discussionId = aDomainEvent.discussionId();
                }
                public Class<DiscussionStarted> subscribedToEventType() {
                    return DiscussionStarted.class;
                }
            });

        discussion = forum.startDiscussion(
                DomainRegistry.forumIdentityService(),
                new Author("jdoe", "John Doe", "jdoe@saasovation.com"),
                "All About DDD");

        DomainRegistry.discussionRepository().save(discussion);

        DomainEventPublisher
            .instance()
            .subscribe(new DomainEventSubscriber<PostedToDiscussion>() {
            public void handleEvent(PostedToDiscussion aDomainEvent) {
                tenant = aDomainEvent.tenant();
                forumId = aDomainEvent.forumId();
                discussionId = aDomainEvent.discussionId();
                postId = aDomainEvent.postId();
                subject = aDomainEvent.subject();
                bodyText = aDomainEvent.bodyText();
            }
            public Class<PostedToDiscussion> subscribedToEventType() {
                return PostedToDiscussion.class;
            }
        });

        post = discussion.post(
                DomainRegistry.forumIdentityService(),
                new Author("jdoe", "John Doe", "jdoe@saasovation.com"),
                "All About DDD",
                "I'd like to start a discussion all about doing domain-driven design.");

        postAgain = discussion.post(
                DomainRegistry.forumIdentityService(),
                new Author("bobw", "Bob Williams", "bobw@saasovation.com"),
                "RE: All About DDD",
                "Well, I think it's a great idea!");

        DomainRegistry.postRepository().save(post);
        DomainRegistry.postRepository().save(postAgain);

        assertNotNull(tenant);
        assertEquals(forum.tenant(), tenant);
        assertNotNull(forumId);
        assertEquals(forum.forumId(), forumId);
        assertNotNull(discussionId);
        assertEquals(discussion.discussionId(), discussionId);
        assertNotNull(subject);
        assertNotNull(bodyText);

        assertNotNull(postId);
        assertTrue(postAgain.subject().equals("RE: All About DDD"));
        assertTrue(postAgain.author().identity().equals("bobw"));

        expectedEvents(4);
        expectedEvent(ForumStarted.class);
        expectedEvent(DiscussionStarted.class);
        expectedEvent(PostedToDiscussion.class, 2);
    }
}
