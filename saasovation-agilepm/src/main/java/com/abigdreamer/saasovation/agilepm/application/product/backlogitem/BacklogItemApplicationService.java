package com.abigdreamer.saasovation.agilepm.application.product.backlogitem;

import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemRepository;

public class BacklogItemApplicationService {

    private BacklogItemRepository backlogItemRepository;

    public BacklogItemApplicationService(
            BacklogItemRepository aBacklogItemRepository) {
        super();

        this.backlogItemRepository = aBacklogItemRepository;
    }

    // TODO: APIs for student assignment

    public void backlogItemPlaceholderService() {
        this.backlogItemRepository();
    }

    private BacklogItemRepository backlogItemRepository() {
        return this.backlogItemRepository;
    }
}
