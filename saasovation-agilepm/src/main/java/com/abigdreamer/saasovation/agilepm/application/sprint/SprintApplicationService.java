package com.abigdreamer.saasovation.agilepm.application.sprint;

import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.*;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.*;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 冲刺应用服务
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:15:52
 * @version V1.0
 */
public class SprintApplicationService {

    private BacklogItemRepository backlogItemRepository;
    private SprintRepository sprintRepository;

    public SprintApplicationService(
            SprintRepository aSprintRepository,
            BacklogItemRepository aBacklogItemRepository) {

        super();

        this.backlogItemRepository = aBacklogItemRepository;
        this.sprintRepository = aSprintRepository;
    }

    public void commitBacklogItemToSprint(
            CommitBacklogItemToSprintCommand aCommand) {

        TenantId tenantId = new TenantId(aCommand.getTenantId());

        Sprint sprint =
                this.sprintRepository()
                    .sprintOfId(
                            tenantId,
                            new SprintId(aCommand.getSprintId()));

        BacklogItem backlogItem =
                this.backlogItemRepository()
                    .backlogItemOfId(
                            tenantId,
                            new BacklogItemId(aCommand.getBacklogItemId()));

        sprint.commit(backlogItem);

        this.sprintRepository().save(sprint);
    }

    private BacklogItemRepository backlogItemRepository() {
        return this.backlogItemRepository;
    }

    private SprintRepository sprintRepository() {
        return this.sprintRepository;
    }
}
