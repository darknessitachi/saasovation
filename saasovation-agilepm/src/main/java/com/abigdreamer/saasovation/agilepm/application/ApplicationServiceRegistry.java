package com.abigdreamer.saasovation.agilepm.application;

import com.abigdreamer.infinity.ddd.domain.model.process.TimeConstrainedProcessTrackerRepository;
import com.abigdreamer.infinity.ddd.port.adapter.persistence.leveldb.LevelDBTimeConstrainedProcessTrackerRepository;
import com.abigdreamer.saasovation.agilepm.application.product.ProductApplicationService;
import com.abigdreamer.saasovation.agilepm.domain.model.DomainRegistry;
import com.abigdreamer.saasovation.agilepm.port.adapter.persistence.LevelDBDatabasePath;


public class ApplicationServiceRegistry {

	protected ProductApplicationService productApplicationService;
	protected TimeConstrainedProcessTrackerRepository timeConstrainedProcessTrackerRepository;

	private static ApplicationServiceRegistry instance = new ApplicationServiceRegistry();

	private ApplicationServiceRegistry() {

		this.timeConstrainedProcessTrackerRepository = new LevelDBTimeConstrainedProcessTrackerRepository(LevelDBDatabasePath.agilePMPath());

		this.productApplicationService = new ProductApplicationService(DomainRegistry.productRepository(), DomainRegistry.productOwnerRepository(),
				this.timeConstrainedProcessTrackerRepository);
	}

	public static ProductApplicationService productApplicationService() {
		return instance.productApplicationService;
	}
}
