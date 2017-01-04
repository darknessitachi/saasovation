package com.saasovation.collaboration.application.forum;

import java.util.Collection;

import javax.sql.DataSource;

import com.abigdreamer.infinity.ddd.port.adapter.persistence.AbstractQueryService;
import com.abigdreamer.infinity.ddd.port.adapter.persistence.JoinOn;

import com.saasovation.collaboration.application.forum.data.ForumData;
import com.saasovation.collaboration.application.forum.data.ForumDiscussionsData;

/**
 * 论坛查询服务
 * 
 * @author Darkness
 * @date 2014-5-31 下午2:38:04
 * @version V1.0
 */
public class ForumQueryService extends AbstractQueryService {

    public ForumQueryService(DataSource aDataSource) {
        super(aDataSource);
    }

    /**
     * 查询所有的论坛数据
     * @param aTenantId
     * @return
     */
    public Collection<ForumData> allForumsDataOfTenant(String aTenantId) {
        return this.queryObjects(
                ForumData.class,
                "select * from tbl_vw_forum where tenant_id = ?",
                new JoinOn(),
                aTenantId);
    }

    /**
     * 查询单个论坛数据
     * @param aTenantId
     * @param aForumId
     * @return
     */
    public ForumData forumDataOfId(String aTenantId, String aForumId) {
        return this.queryObject(
                ForumData.class,
                "select * from tbl_vw_forum where tenant_id = ? and forum_id = ?",
                new JoinOn(),
                aTenantId,
                aForumId);
    }

    /**
     * 查询论坛讨论数据
     * @param aTenantId
     * @param aForumId
     * @return
     */
    public ForumDiscussionsData forumDiscussionsDataOfId(String aTenantId, String aForumId) {
        return this.queryObject(
                ForumDiscussionsData.class,
                "select "
                +  "forum.closed, forum.creator_email_address, forum.creator_identity, "
                +  "forum.creator_name, forum.description, forum.exclusive_owner, forum.forum_id, "
                +  "forum.moderator_email_address, forum.moderator_identity, forum.moderator_name, "
                +  "forum.subject, forum.tenant_id, "
                +  "disc.author_email_address as o_discussions_author_email_address, "
                +  "disc.author_identity as o_discussions_author_identity, "
                +  "disc.author_name as o_discussions_author_name, "
                +  "disc.closed as o_discussions_closed, "
                +  "disc.discussion_id as o_discussions_discussion_id, "
                +  "disc.exclusive_owner as o_discussions_exclusive_owner, "
                +  "disc.forum_id as o_discussions_forum_id, "
                +  "disc.subject as o_discussions_subject, "
                +  "disc.tenant_id as o_discussions_tenant_id "
                + "from tbl_vw_forum as forum left outer join tbl_vw_discussion as disc "
                + " on forum.forum_id = disc.forum_id "
                + "where (forum.tenant_id = ? and forum.forum_id = ?)",
                new JoinOn("forum_id", "o_discussions_forum_id"),
                aTenantId,
                aForumId);
    }

    public String forumIdOfExclusiveOwner(String aTenantId, String anExclusiveOwner) {
        return this.queryString(
                "select forum_id from tbl_vw_forum where tenant_id = ? and exclusive_owner = ?",
                aTenantId,
                anExclusiveOwner);
    }
}
