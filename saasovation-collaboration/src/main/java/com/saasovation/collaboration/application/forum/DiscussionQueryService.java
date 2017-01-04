package com.saasovation.collaboration.application.forum;

import java.util.Collection;

import javax.sql.DataSource;

import com.abigdreamer.infinity.ddd.port.adapter.persistence.AbstractQueryService;
import com.abigdreamer.infinity.ddd.port.adapter.persistence.JoinOn;

import com.saasovation.collaboration.application.forum.data.DiscussionData;
import com.saasovation.collaboration.application.forum.data.DiscussionPostsData;

/**
 * 讨论查询服务
 * 
 * @author Darkness
 * @date 2014-5-31 下午1:55:54
 * @version V1.0
 */
public class DiscussionQueryService extends AbstractQueryService {

    public DiscussionQueryService(DataSource aDataSource) {
        super(aDataSource);
    }

    /**
     * 查询论坛中的所有讨论数据
     * @param aTenantId
     * @param aForumId
     * @return
     */
    public Collection<DiscussionData> allDiscussionsDataOfForum(String aTenantId, String aForumId) {
        return this.queryObjects(
                DiscussionData.class,
                "select * from tbl_vw_discussion where tenant_id = ? and forum_id = ?",
                new JoinOn(),
                aTenantId,
                aForumId);
    }

    /**
     * 根据Id查询讨论数据
     * @param aTenantId
     * @param aDiscussionId
     * @return
     */
    public DiscussionData discussionDataOfId(String aTenantId, String aDiscussionId) {
        return this.queryObject(
                DiscussionData.class,
                "select * from tbl_vw_discussion where tenant_id = ? and discussion_id = ?",
                new JoinOn(),
                aTenantId,
                aDiscussionId);
    }

    public String discussionIdOfExclusiveOwner(String aTenantId, String anExclusiveOwner) {
        return this.queryString(
                "select discussion_id from tbl_vw_discussion where tenant_id = ? and exclusive_owner = ?",
                aTenantId,
                anExclusiveOwner);
    }

    /**
     * 查询讨论的所有提交
     * @param aTenantId
     * @param aDiscussionId
     * @return
     */
    public DiscussionPostsData discussionPostsDataOfId(String aTenantId, String aDiscussionId) {
        return this.queryObject(
                DiscussionPostsData.class,
                "select "
                +  "disc.author_email_address, disc.author_identity, disc.author_name, "
                +  "disc.closed, disc.discussion_id, disc.exclusive_owner, "
                +  "disc.forum_id, disc.subject, disc.tenant_id, "
                +  "post.author_email_address as o_posts_author_email_address, "
                +  "post.author_identity as o_posts_author_identity, "
                +  "post.author_name as o_posts_author_name, "
                +  "post.body_text as o_posts_body_text, post.changed_on as o_posts_changed_on, "
                +  "post.created_on as o_posts_created_on, "
                +  "post.discussion_id as o_posts_discussion_id, "
                +  "post.forum_id as o_posts_forum_id, post.post_id as o_posts_post_id, "
                +  "post.reply_to_post_id as o_posts_reply_to_post_id, post.subject as o_posts_subject, "
                +  "post.tenant_id as o_posts_tenant_id "
                + "from tbl_vw_discussion as disc left outer join tbl_vw_post as post "
                + " on disc.discussion_id = post.discussion_id "
                + "where (disc.tenant_id = ? and disc.discussion_id = ?)",
                new JoinOn("discussion_id", "o_posts_discussion_id"),
                aTenantId,
                aDiscussionId);
    }
}
