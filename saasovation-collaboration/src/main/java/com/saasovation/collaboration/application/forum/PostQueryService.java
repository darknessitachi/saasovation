package com.saasovation.collaboration.application.forum;

import java.util.Collection;

import javax.sql.DataSource;

import com.abigdreamer.infinity.ddd.port.adapter.persistence.AbstractQueryService;
import com.abigdreamer.infinity.ddd.port.adapter.persistence.JoinOn;

import com.saasovation.collaboration.application.forum.data.PostData;

/**
 * 帖子查询服务
 * 
 * @author Darkness
 * @date 2014-5-31 下午2:39:54
 * @version V1.0
 */
public class PostQueryService extends AbstractQueryService {

    public PostQueryService(DataSource aDataSource) {
        super(aDataSource);
    }

    /**
     * 查询讨论下的所有帖子
     * @param aTenantId
     * @param aDiscussionId
     * @return
     */
    public Collection<PostData> allPostsDataOfDiscussion(String aTenantId, String aDiscussionId) {
        return this.queryObjects(
                PostData.class,
                "select * from tbl_vw_post where tenant_id = ? and discussion_id = ?",
                new JoinOn(),
                aTenantId,
                aDiscussionId);
    }

    /**
     * 查询单个帖子
     * @param aTenantId
     * @param aPostId
     * @return
     */
    public PostData postDataOfId(String aTenantId, String aPostId) {
        return this.queryObject(
                PostData.class,
                "select * from tbl_vw_post where tenant_id = ? and post_id = ?",
                new JoinOn(),
                aTenantId,
                aPostId);
    }
}
