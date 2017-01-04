package org.infinite.identityaccess.application;

import com.abigdreamer.infinity.ddd.notification.application.NotificationApplicationService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 应用服务注册表
 * 
 * @author Darkness
 * @date 2014-5-28 下午10:20:38
 * @version V1.0
 */
public class ApplicationServiceRegistry implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public static AccessApplicationService accessApplicationService() {
		return (AccessApplicationService) applicationContext.getBean("accessApplicationService");
	}

	public static IdentityApplicationService identityApplicationService() {
		IdentityApplicationService identityApplicationService = (IdentityApplicationService) applicationContext.getBean("identityApplicationService");
		return identityApplicationService;
	}

	public static NotificationApplicationService notificationApplicationService() {
		return (NotificationApplicationService) applicationContext.getBean("notificationApplicationService");
	}

	@Override
	public synchronized void setApplicationContext(ApplicationContext anApplicationContext) throws BeansException {

		if (ApplicationServiceRegistry.applicationContext == null) {
			ApplicationServiceRegistry.applicationContext = anApplicationContext;
		}
	}
}
