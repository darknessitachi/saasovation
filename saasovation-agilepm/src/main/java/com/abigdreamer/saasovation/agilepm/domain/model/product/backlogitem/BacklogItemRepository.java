package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Collection;

import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.ReleaseId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.SprintId;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;

/**
 * 待办项仓储
 * 
 * @author Darkness
 * @date 2014-12-2 下午6:50:34 
 * @version V1.0
 */
public interface BacklogItemRepository {

    public Collection<BacklogItem> allBacklogItemsComittedTo(TenantId aTenantId, SprintId aSprintId);

    public Collection<BacklogItem> allBacklogItemsScheduledFor(TenantId aTenantId, ReleaseId aReleaseId);

    public Collection<BacklogItem> allOutstandingProductBacklogItems(TenantId aTenantId, ProductId aProductId);

    public Collection<BacklogItem> allProductBacklogItems(TenantId aTenantId, ProductId aProductId);

    public BacklogItem backlogItemOfId(TenantId aTenantId, BacklogItemId aBacklogItemId);

    public BacklogItemId nextIdentity();

    public void remove(BacklogItem aBacklogItem);

    public void removeAll(Collection<BacklogItem> aBacklogItemCollection);

    public void save(BacklogItem aBacklogItem);

    public void saveAll(Collection<BacklogItem> aBacklogItemCollection);
}
