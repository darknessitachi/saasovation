package org.infinite.identityaccess.domain.service;

import java.util.Iterator;

import org.infinite.identityaccess.domain.model.identity.Group;
import org.infinite.identityaccess.domain.model.identity.GroupMember;
import org.infinite.identityaccess.domain.model.identity.User;
import org.infinite.identityaccess.domain.repository.GroupRepository;
import org.infinite.identityaccess.domain.repository.UserRepository;


/**
 * 成员服务
 * 
 * @author Darkness
 * @date 2014-5-27 下午5:41:56
 * @version V1.0
 */
public class GroupMemberService {

    private GroupRepository groupRepository;
    private UserRepository userRepository;

    public GroupMemberService(
            UserRepository aUserRepository,
            GroupRepository aGroupRepository) {

        super();

        this.groupRepository = aGroupRepository;
        this.userRepository = aUserRepository;
    }

    public boolean confirmUser(Group aGroup, User aUser) {
        boolean userConfirmed = true;

        User confirmedUser =
                this.userRepository()
                    .userWithUsername(aGroup.tenantId(), aUser.username());

        if (confirmedUser == null || !confirmedUser.isEnabled()) {
            userConfirmed = false;
        }

        return userConfirmed;
    }

    public boolean isMemberGroup(Group aGroup, GroupMember aMemberGroup) {
        boolean isMember = false;

        Iterator<GroupMember> iter =
            aGroup.groupMembers().iterator();

        while (!isMember && iter.hasNext()) {
            GroupMember member = iter.next();
            
            if (!member.isGroup()) {
                continue;
            }
            
            if (aMemberGroup.equals(member)) {
                isMember = true;
            } else {
                Group group =
                    this.groupRepository()
                        .groupNamed(member.tenantId(), member.name());
                if (group != null) {
                    isMember = this.isMemberGroup(group, aMemberGroup);
                }
            }
        }

        return isMember;
    }

    public boolean isUserInNestedGroup(Group aGroup, User aUser) {
        boolean isInNestedGroup = false;

        Iterator<GroupMember> iter =
            aGroup.groupMembers().iterator();

        while (!isInNestedGroup && iter.hasNext()) {
            GroupMember member = iter.next();
            if (member.isGroup()) {
                Group group =
                        this.groupRepository()
                            .groupNamed(member.tenantId(), member.name());
                if (group != null) {
                    isInNestedGroup = group.isMember(aUser, this);
                }
            }
        }

        return isInNestedGroup;
    }

    private GroupRepository groupRepository() {
        return this.groupRepository;
    }

    private UserRepository userRepository() {
        return this.userRepository;
    }
}
