package com.abigdreamer.saasovation.agilepm.port.adapter.persistence;

import java.util.Collection;
import java.util.Date;

import junit.framework.TestCase;

import org.iq80.leveldb.DB;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.persistence.leveldb.LevelDBProvider;
import com.abigdreamer.infinity.persistence.leveldb.LevelDBUnitOfWork;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItem;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemRepository;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemStatus;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemType;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.StoryPoints;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.Release;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.ReleaseId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.Sprint;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.SprintId;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;
import com.abigdreamer.saasovation.agilepm.port.adapter.persistence.LevelDBBacklogItemRepository;
import com.abigdreamer.saasovation.agilepm.port.adapter.persistence.LevelDBDatabasePath;


public class LevelDBBacklogItemRepositoryTest extends TestCase {

    private DB database;
    private BacklogItemRepository backlogItemRepository = new LevelDBBacklogItemRepository();

    public LevelDBBacklogItemRepositoryTest() {
        super();
    }

    public void testSaveWithTypes() throws Exception {
        BacklogItem backlogItem1 =
                new BacklogItem(
                        new TenantId("12345"),
                        new ProductId("67890"),
                        new BacklogItemId("bli1"),
                        "My backlog item 1.",
                        "Domain Model",
                        BacklogItemType.FEATURE,
                        BacklogItemStatus.PLANNED,
                        StoryPoints.EIGHT);

        LevelDBUnitOfWork.start(this.database);
        backlogItemRepository.save(backlogItem1);
        LevelDBUnitOfWork.current().commit();

        BacklogItem savedBacklogItem =
                backlogItemRepository
                    .backlogItemOfId(
                            backlogItem1.tenantId(),
                            backlogItem1.backlogItemId());

        assertNotNull(savedBacklogItem);
        assertEquals(backlogItem1.tenantId(), savedBacklogItem.tenantId());
        assertEquals(backlogItem1.productId(), savedBacklogItem.productId());
        assertEquals(backlogItem1.summary(), savedBacklogItem.summary());
        assertEquals(backlogItem1.category(), savedBacklogItem.category());
        assertEquals(backlogItem1.type(), savedBacklogItem.type());
        assertEquals(backlogItem1.storyPoints(), savedBacklogItem.storyPoints());

        Collection<BacklogItem> savedBacklogItems =
                backlogItemRepository
                    .allProductBacklogItems(backlogItem1.tenantId(), backlogItem1.productId());

        assertFalse(savedBacklogItems.isEmpty());
        assertEquals(1, savedBacklogItems.size());

        BacklogItem backlogItem2 =
                new BacklogItem(
                        new TenantId("12345"),
                        new ProductId("67890"),
                        new BacklogItemId("bli2"),
                        "My backlog item 1.",
                        "Domain Model",
                        BacklogItemType.FEATURE,
                        BacklogItemStatus.DONE,
                        StoryPoints.EIGHT);

        LevelDBUnitOfWork.start(this.database);
        backlogItemRepository.save(backlogItem2);
        LevelDBUnitOfWork.current().commit();

        savedBacklogItems =
                backlogItemRepository
                    .allProductBacklogItems(backlogItem1.tenantId(), backlogItem1.productId());

        assertFalse(savedBacklogItems.isEmpty());
        assertEquals(2, savedBacklogItems.size());

        savedBacklogItems =
                backlogItemRepository
                    .allOutstandingProductBacklogItems(backlogItem1.tenantId(), backlogItem1.productId());

        assertFalse(savedBacklogItems.isEmpty());
        assertEquals(1, savedBacklogItems.size());
    }

    public void testScheduledAndCommittedBacklogItems() throws Exception {
        BacklogItem backlogItem1 =
                new BacklogItem(
                        new TenantId("12345"),
                        new ProductId("p00000"),
                        new BacklogItemId("bli1"),
                        "My backlog item 1.",
                        "Domain Model",
                        BacklogItemType.FEATURE,
                        BacklogItemStatus.PLANNED,
                        StoryPoints.EIGHT);

        BacklogItem backlogItem2 =
                new BacklogItem(
                        new TenantId("12345"),
                        new ProductId("p00000"),
                        new BacklogItemId("bli2"),
                        "My backlog item 1.",
                        "Domain Model",
                        BacklogItemType.FEATURE,
                        BacklogItemStatus.PLANNED,
                        StoryPoints.EIGHT);

        BacklogItem backlogItem3 =
                new BacklogItem(
                        new TenantId("12345"),
                        new ProductId("p00000"),
                        new BacklogItemId("bli3"),
                        "My backlog item 1.",
                        "Domain Model",
                        BacklogItemType.FEATURE,
                        BacklogItemStatus.PLANNED,
                        StoryPoints.EIGHT);

        Release release = new Release(
                new TenantId("12345"), new ProductId("p00000"), new ReleaseId("r11111"),
                "release1", "My release 1.", new Date(), new Date());

        Sprint sprint = new Sprint(
                new TenantId("12345"), new ProductId("p00000"), new SprintId("s11111"),
                "sprint1", "My sprint 1.", new Date(), new Date());

        backlogItem2.scheduleFor(release);
        backlogItem2.commitTo(sprint);

        backlogItem3.scheduleFor(release);

        LevelDBUnitOfWork.start(this.database);
        backlogItemRepository.save(backlogItem1);
        backlogItemRepository.save(backlogItem2);
        backlogItemRepository.save(backlogItem3);
        LevelDBUnitOfWork.current().commit();

        Collection<BacklogItem> savedBacklogItems =
                backlogItemRepository
                    .allProductBacklogItems(backlogItem1.tenantId(), backlogItem1.productId());

        assertFalse(savedBacklogItems.isEmpty());
        assertEquals(3, savedBacklogItems.size());

        savedBacklogItems =
                backlogItemRepository
                    .allOutstandingProductBacklogItems(backlogItem1.tenantId(), backlogItem1.productId());

        assertFalse(savedBacklogItems.isEmpty());
        assertEquals(3, savedBacklogItems.size());

        savedBacklogItems =
                backlogItemRepository
                    .allBacklogItemsComittedTo(sprint.tenantId(), sprint.sprintId());

        assertFalse(savedBacklogItems.isEmpty());
        assertEquals(1, savedBacklogItems.size());

        savedBacklogItems =
                backlogItemRepository
                    .allBacklogItemsScheduledFor(release.tenantId(), release.releaseId());

        assertFalse(savedBacklogItems.isEmpty());
        assertEquals(2, savedBacklogItems.size());

        LevelDBUnitOfWork.start(this.database);
        backlogItemRepository.remove(backlogItem2);
        LevelDBUnitOfWork.current().commit();

        savedBacklogItems =
                backlogItemRepository
                    .allOutstandingProductBacklogItems(backlogItem1.tenantId(), backlogItem1.productId());

        assertFalse(savedBacklogItems.isEmpty());
        assertEquals(2, savedBacklogItems.size());

        savedBacklogItems =
                backlogItemRepository
                    .allBacklogItemsComittedTo(sprint.tenantId(), sprint.sprintId());

        assertTrue(savedBacklogItems.isEmpty());

        savedBacklogItems =
                backlogItemRepository
                    .allBacklogItemsScheduledFor(release.tenantId(), release.releaseId());

        assertFalse(savedBacklogItems.isEmpty());
        assertEquals(1, savedBacklogItems.size());

        LevelDBUnitOfWork.start(this.database);
        backlogItemRepository.remove(backlogItem3);
        LevelDBUnitOfWork.current().commit();

        savedBacklogItems =
                backlogItemRepository
                    .allBacklogItemsScheduledFor(release.tenantId(), release.releaseId());

        assertTrue(savedBacklogItems.isEmpty());

        savedBacklogItems =
                backlogItemRepository
                    .allOutstandingProductBacklogItems(backlogItem1.tenantId(), backlogItem1.productId());

        assertFalse(savedBacklogItems.isEmpty());
        assertEquals(1, savedBacklogItems.size());
    }

    @Override
    protected void setUp() throws Exception {
        DomainEventPublisher.instance().reset();

        this.database = LevelDBProvider.instance().databaseFrom(LevelDBDatabasePath.agilePMPath());

        LevelDBProvider.instance().purge(this.database);

        super.setUp();
    }
}
