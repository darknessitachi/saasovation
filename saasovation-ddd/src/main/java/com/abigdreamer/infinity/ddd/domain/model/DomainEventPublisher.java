package com.abigdreamer.infinity.ddd.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *  事件发布器
 * 
 * @author Darkness
 * @date 2014-5-5 下午7:30:50 
 * @version V1.0
 */
public class DomainEventPublisher {

	private static final ThreadLocal<DomainEventPublisher> instance = new ThreadLocal<DomainEventPublisher>() {
		@Override
		protected DomainEventPublisher initialValue() {
			return new DomainEventPublisher();
		}
	};

	private boolean publishing;

	// 订阅方列表
	@SuppressWarnings("rawtypes")
	private List subscribers;

	public static DomainEventPublisher instance() {
		return instance.get();
	}

	public <T> void publish(final T aDomainEvent) {
		if (!this.isPublishing() && this.hasSubscribers()) {

			try {
				this.setPublishing(true);

				Class<?> eventType = aDomainEvent.getClass();

				@SuppressWarnings("unchecked")
				List<DomainEventSubscriber<T>> allSubscribers = this.subscribers();

				for (DomainEventSubscriber<T> subscriber : allSubscribers) {
					Class<T> subscribedToType = subscriber.subscribedToEventType();

					if (eventType == subscribedToType || subscribedToType == DomainEvent.class) {
						subscriber.handleEvent(aDomainEvent);
					}
				}

			} finally {
				this.setPublishing(false);
			}
		}
	}

	public void publishAll(Collection<DomainEvent> aDomainEvents) {
		for (DomainEvent domainEvent : aDomainEvents) {
			this.publish(domainEvent);
		}
	}

	/**
	 * 清空所有监听
	 * 
	 * @author Darkness
	 * @date 2014-12-16 下午7:10:02 
	 * @version V1.0
	 */
	public void reset() {
		if (!this.isPublishing()) {
			this.setSubscribers(null);
		}
	}

	/**
	 *  注册订阅方
	 * 
	 * @author Darkness
	 * @date 2014-5-5 下午7:48:27 
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public <T> void subscribe(DomainEventSubscriber<T> aSubscriber) {
		if (!this.isPublishing()) {
			this.ensureSubscribersList();

			this.subscribers().add(aSubscriber);
		}
	}

	private DomainEventPublisher() {
		super();

		this.setPublishing(false);
		this.ensureSubscribersList();
	}

	@SuppressWarnings("rawtypes")
	private void ensureSubscribersList() {
		if (!this.hasSubscribers()) {
			this.setSubscribers(new ArrayList());
		}
	}

	private boolean isPublishing() {
		return this.publishing;
	}

	private void setPublishing(boolean aFlag) {
		this.publishing = aFlag;
	}

	private boolean hasSubscribers() {
		return this.subscribers() != null;
	}

	@SuppressWarnings("rawtypes")
	private List subscribers() {
		return this.subscribers;
	}

	@SuppressWarnings("rawtypes")
	private void setSubscribers(List aSubscriberList) {
		this.subscribers = aSubscriberList;
	}
}
