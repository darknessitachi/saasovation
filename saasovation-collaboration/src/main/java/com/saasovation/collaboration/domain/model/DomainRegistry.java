package com.saasovation.collaboration.domain.model;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.saasovation.collaboration.domain.model.calendar.CalendarEntryRepository;
import com.saasovation.collaboration.domain.model.calendar.CalendarIdentityService;
import com.saasovation.collaboration.domain.model.calendar.CalendarRepository;
import com.saasovation.collaboration.domain.model.collaborator.CollaboratorService;
import com.saasovation.collaboration.domain.model.forum.DiscussionRepository;
import com.saasovation.collaboration.domain.model.forum.ForumIdentityService;
import com.saasovation.collaboration.domain.model.forum.ForumRepository;
import com.saasovation.collaboration.domain.model.forum.PostRepository;

/**
 * 领域注册表
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:39:45
 * @version V1.0
 */
public class DomainRegistry implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static CalendarIdentityService calendarIdentityService() {
        return (CalendarIdentityService) applicationContext.getBean("calendarIdentityService");
    }

    public static CalendarEntryRepository calendarEntryRepository() {
        return (CalendarEntryRepository) applicationContext.getBean("calendarEntryRepository");
    }

    public static CalendarRepository calendarRepository() {
        return (CalendarRepository) applicationContext.getBean("calendarRepository");
    }

    public static CollaboratorService collaboratorService() {
        return (CollaboratorService) applicationContext.getBean("collaboratorService");
    }

    public static DiscussionRepository discussionRepository() {
        return (DiscussionRepository) applicationContext.getBean("discussionRepository");
    }

    public static ForumIdentityService forumIdentityService() {
        return (ForumIdentityService) applicationContext.getBean("forumIdentityService");
    }

    public static ForumRepository forumRepository() {
        return (ForumRepository) applicationContext.getBean("forumRepository");
    }

    public static PostRepository postRepository() {
        return (PostRepository) applicationContext.getBean("postRepository");
    }

    @Override
    public synchronized void setApplicationContext(ApplicationContext anApplicationContext) throws BeansException {
        if (DomainRegistry.applicationContext == null) {
            DomainRegistry.applicationContext = anApplicationContext;
        }
    }
}
