package org.infinite.identityaccess.resource;

import java.math.BigInteger;
import java.security.MessageDigest;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;

import com.abigdreamer.infinity.ddd.notification.application.NotificationApplicationService;
import org.infinite.identityaccess.application.AccessApplicationService;
import org.infinite.identityaccess.application.ApplicationServiceRegistry;
import org.infinite.identityaccess.application.IdentityApplicationService;
import org.infinite.identityaccess.domain.model.identity.User;


/**
 * 抽象资源
 * 
 * @author Darkness
 * @date 2014-5-28 下午10:26:19
 * @version V1.0
 */
public class AbstractResource {

    public AbstractResource() {
        super();
    }

    protected AccessApplicationService accessApplicationService() {
        return ApplicationServiceRegistry.accessApplicationService();
    }

    protected IdentityApplicationService identityApplicationService() {
        return ApplicationServiceRegistry.identityApplicationService();
    }

    protected NotificationApplicationService notificationApplicationService() {
        return ApplicationServiceRegistry.notificationApplicationService();
    }

    protected CacheControl cacheControlFor(int aNumberOfSeconds) {
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(aNumberOfSeconds);
        return cacheControl;
    }
    
    protected EntityTag userETag(User aUser) {

        EntityTag tag = null;

        int hashCode = aUser.hashCode() + aUser.person().hashCode();

        try {
            // change this algorithm as needed
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(Integer.toString(hashCode).getBytes("UTF-8"));
            BigInteger digestValue = new BigInteger(1, messageDigest.digest());
            String strongHash = digestValue.toString(16);

            tag = new EntityTag(strongHash);

        } catch (Throwable t) {
            tag = new EntityTag(Integer.toString(hashCode));
        }

        return tag;
    }
}
