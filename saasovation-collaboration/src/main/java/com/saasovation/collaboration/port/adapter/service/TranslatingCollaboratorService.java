package com.saasovation.collaboration.port.adapter.service;

import com.saasovation.collaboration.domain.model.collaborator.Author;
import com.saasovation.collaboration.domain.model.collaborator.CollaboratorService;
import com.saasovation.collaboration.domain.model.collaborator.Creator;
import com.saasovation.collaboration.domain.model.collaborator.Moderator;
import com.saasovation.collaboration.domain.model.collaborator.Owner;
import com.saasovation.collaboration.domain.model.collaborator.Participant;
import com.saasovation.collaboration.domain.model.tenant.Tenant;

public class TranslatingCollaboratorService implements CollaboratorService {

	private UserInRoleAdapter userInRoleAdapter;

	public TranslatingCollaboratorService(UserInRoleAdapter aUserInRoleAdapter) {
		super();

		this.userInRoleAdapter = aUserInRoleAdapter;
	}

	@Override
	public Author authorFrom(Tenant aTenant, String anIdentity) {
		Author author = this.userInRoleAdapter().toCollaborator(aTenant, anIdentity, "Author", Author.class);

		return author;
	}

	@Override
	public Creator creatorFrom(Tenant aTenant, String anIdentity) {
		Creator creator = this.userInRoleAdapter().toCollaborator(aTenant, anIdentity, "Creator", Creator.class);

		return creator;
	}

	@Override
	public Moderator moderatorFrom(Tenant aTenant, String anIdentity) {
		Moderator moderator = this.userInRoleAdapter().toCollaborator(aTenant, anIdentity, "Moderator", Moderator.class);

		return moderator;
	}

	@Override
	public Owner ownerFrom(Tenant aTenant, String anIdentity) {
		Owner owner = this.userInRoleAdapter().toCollaborator(aTenant, anIdentity, "Owner", Owner.class);

		return owner;
	}

	@Override
	public Participant participantFrom(Tenant aTenant, String anIdentity) {
		Participant participant = this.userInRoleAdapter().toCollaborator(aTenant, anIdentity, "Participant", Participant.class);

		return participant;
	}

	private UserInRoleAdapter userInRoleAdapter() {
		return this.userInRoleAdapter;
	}
}
