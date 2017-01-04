package org.infinite.identityaccess.domain;

import org.infinite.identityaccess.domain.repository.GroupRepository;
import org.infinite.identityaccess.domain.repository.RoleRepository;
import org.infinite.identityaccess.domain.repository.TenantRepository;
import org.infinite.identityaccess.domain.repository.UserRepository;
import org.infinite.identityaccess.domain.service.AuthenticationService;
import org.infinite.identityaccess.domain.service.AuthorizationService;
import org.infinite.identityaccess.domain.service.EncryptionService;
import org.infinite.identityaccess.domain.service.GroupMemberService;
import org.infinite.identityaccess.domain.service.PasswordService;
import org.infinite.identityaccess.domain.service.TenantProvisioningService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 领域注册表
 * 
 * @author Darkness
 * @date 2014-5-27 下午7:54:18
 * @version V1.0
 */
public class DomainRegistry implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static AuthenticationService authenticationService() {
        return (AuthenticationService) applicationContext.getBean("authenticationService");
    }

    public static AuthorizationService authorizationService() {
        return (AuthorizationService) applicationContext.getBean("authorizationService");
    }

    public static EncryptionService encryptionService() {
        return (EncryptionService) applicationContext.getBean("encryptionService");
    }

    public static GroupMemberService groupMemberService() {
        return (GroupMemberService) applicationContext.getBean("groupMemberService");
    }

    public static GroupRepository groupRepository() {
        return (GroupRepository) applicationContext.getBean("groupRepository");
    }

    public static PasswordService passwordService() {
        return (PasswordService) applicationContext.getBean("passwordService");
    }

    public static RoleRepository roleRepository() {
        return (RoleRepository) applicationContext.getBean("roleRepository");
    }

    public static TenantProvisioningService tenantProvisioningService() {
        return (TenantProvisioningService) applicationContext.getBean("tenantProvisioningService");
    }

    public static TenantRepository tenantRepository() {
        return (TenantRepository) applicationContext.getBean("tenantRepository");
    }

    public static UserRepository userRepository() {
        return (UserRepository) applicationContext.getBean("userRepository");
    }

    @Override
    public synchronized void setApplicationContext(ApplicationContext anApplicationContext) throws BeansException {

        if (DomainRegistry.applicationContext == null) {
            DomainRegistry.applicationContext = anApplicationContext;
        }
    }
}
