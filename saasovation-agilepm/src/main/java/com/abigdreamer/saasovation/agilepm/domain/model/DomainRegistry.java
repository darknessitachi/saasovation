package com.abigdreamer.saasovation.agilepm.domain.model;

import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductRepository;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemRepository;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.ReleaseRepository;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.SprintRepository;
import com.abigdreamer.saasovation.agilepm.domain.model.team.ProductOwnerRepository;
import com.abigdreamer.saasovation.agilepm.domain.model.team.TeamMemberRepository;
import com.abigdreamer.saasovation.agilepm.domain.model.team.TeamRepository;
import com.abigdreamer.saasovation.agilepm.port.adapter.persistence.LevelDBBacklogItemRepository;
import com.abigdreamer.saasovation.agilepm.port.adapter.persistence.LevelDBProductOwnerRepository;
import com.abigdreamer.saasovation.agilepm.port.adapter.persistence.LevelDBProductRepository;
import com.abigdreamer.saasovation.agilepm.port.adapter.persistence.LevelDBReleaseRepository;
import com.abigdreamer.saasovation.agilepm.port.adapter.persistence.LevelDBSprintRepository;
import com.abigdreamer.saasovation.agilepm.port.adapter.persistence.LevelDBTeamMemberRepository;
import com.abigdreamer.saasovation.agilepm.port.adapter.persistence.LevelDBTeamRepository;

public class DomainRegistry {

	protected ProductOwnerRepository productOwnerRepository;
	protected ProductRepository productRepository;
	protected ReleaseRepository releaseRepository;
	protected SprintRepository sprintRepository;
	protected TeamMemberRepository teamMemberRepository;
	protected TeamRepository teamRepository;
	protected BacklogItemRepository backlogItemRepository;

	private static DomainRegistry instance = new DomainRegistry();

	private DomainRegistry() {
		this.backlogItemRepository = new LevelDBBacklogItemRepository();
		this.productOwnerRepository = new LevelDBProductOwnerRepository();
		this.productRepository = new LevelDBProductRepository();
		this.releaseRepository = new LevelDBReleaseRepository();
		this.sprintRepository = new LevelDBSprintRepository();
		this.teamMemberRepository = new LevelDBTeamMemberRepository();
		this.teamRepository = new LevelDBTeamRepository();
	}

	public static ProductOwnerRepository productOwnerRepository() {
		return instance.productOwnerRepository;
	}

	public static ProductRepository productRepository() {
		return instance.productRepository;
	}

	public static ReleaseRepository releaseRepository() {
		return instance.releaseRepository;
	}

	public static SprintRepository sprintRepository() {
		return instance.sprintRepository;
	}

	public static TeamMemberRepository teamMemberRepository() {
		return instance.teamMemberRepository;
	}

	public static TeamRepository teamRepository() {
		return instance.teamRepository;
	}

	public static BacklogItemRepository backlogItemRepository() {
		return instance.backlogItemRepository;
	}
}
