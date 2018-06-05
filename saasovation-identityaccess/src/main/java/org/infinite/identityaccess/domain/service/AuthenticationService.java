package org.infinite.identityaccess.domain.service;

import org.infinite.identityaccess.domain.model.identity.Tenant;
import org.infinite.identityaccess.domain.model.identity.TenantId;
import org.infinite.identityaccess.domain.model.identity.User;
import org.infinite.identityaccess.domain.model.identity.UserDescriptor;
import org.infinite.identityaccess.domain.repository.TenantRepository;
import org.infinite.identityaccess.domain.repository.UserRepository;

import com.rapidark.framework.commons.lang.AssertionConcern;


/**
 * 验证服务
 * 
 * @author Darkness
 * @date 2014-5-28 下午9:31:59
 * @version V1.0
 */
public class AuthenticationService extends AssertionConcern {

    private EncryptionService encryptionService;
    private TenantRepository tenantRepository;
    private UserRepository userRepository;

    public AuthenticationService(
            TenantRepository aTenantRepository,
            UserRepository aUserRepository,
            EncryptionService anEncryptionService) {

        super();

        this.encryptionService = anEncryptionService;
        this.tenantRepository = aTenantRepository;
        this.userRepository = aUserRepository;
    }

    /**
     * 验证租户有效
     * @param aTenantId
     * @param aUsername
     * @param aPassword
     * @return
     */
    public UserDescriptor authenticate(TenantId aTenantId, String aUsername, String aPassword) {

        this.assertArgumentNotNull(aTenantId, "TenantId must not be null.");
        this.assertArgumentNotEmpty(aUsername, "Username must be provided.");
        this.assertArgumentNotEmpty(aPassword, "Password must be provided.");

        UserDescriptor userDescriptor = UserDescriptor.nullDescriptorInstance();

        Tenant tenant = this.tenantRepository().tenantOfId(aTenantId);

        if (tenant != null && tenant.isActive()) {
            String encryptedPassword = this.encryptionService().encryptedValue(aPassword);

            User user =
                    this.userRepository()
                        .userFromAuthenticCredentials(
                            aTenantId,
                            aUsername,
                            encryptedPassword);

            if (user != null && user.isEnabled()) {
                userDescriptor = user.userDescriptor();
            }
        }

        return userDescriptor;
    }

    private EncryptionService encryptionService() {
        return this.encryptionService;
    }

    private TenantRepository tenantRepository() {
        return this.tenantRepository;
    }

    private UserRepository userRepository() {
        return this.userRepository;
    }
}
