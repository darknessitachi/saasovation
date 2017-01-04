package com.abigdreamer.infinity.ddd.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventSubscriber;
import com.abigdreamer.infinity.ddd.event.TestableDomainEvent;
import org.junit.Before;
import org.junit.Test;

/**
 * 轻量级领域事件发布器测试
 * 
 * @author Darkness
 * @date 2014-12-23 下午4:29:52 
 * @version V1.0
 */
public class DomainEventPublisherTest {

    private boolean anotherEventHandled;
    private boolean eventHandled;

    @Before
    public void init() {
    	DomainEventPublisher.instance().reset();
    }
    
    @Test
    public void domainEventPublisherPublish() throws Exception {
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<TestableDomainEvent>(){
            @Override
            public void handleEvent(TestableDomainEvent aDomainEvent) {
                assertEquals(100L, aDomainEvent.id());
                assertEquals("test", aDomainEvent.name());
                eventHandled = true;
            }
            @Override
            public Class<TestableDomainEvent> subscribedToEventType() {
                return TestableDomainEvent.class;
            }
        });

        assertFalse(this.eventHandled);

        DomainEventPublisher.instance().publish(new TestableDomainEvent(100L, "test"));

        assertTrue(this.eventHandled);
    }

    @Test
    public void domainEventPublisherBlocked() throws Exception {
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<TestableDomainEvent>(){
            @Override
            public void handleEvent(TestableDomainEvent aDomainEvent) {
                assertEquals(100L, aDomainEvent.id());
                assertEquals("test", aDomainEvent.name());
                eventHandled = true;
                // 不支持嵌套发布消息
                // attempt nested publish, which should not work
                DomainEventPublisher.instance().publish(new AnotherTestableDomainEvent(1000.0));
            }
            @Override
            public Class<TestableDomainEvent> subscribedToEventType() {
                return TestableDomainEvent.class;
            }
        });

        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<AnotherTestableDomainEvent>(){
            @Override
            public void handleEvent(AnotherTestableDomainEvent aDomainEvent) {
                // should never be reached due to blocked publisher
                assertEquals(1000.0, aDomainEvent.value(), 0.0001);
                anotherEventHandled = true;
            }
            @Override
            public Class<AnotherTestableDomainEvent> subscribedToEventType() {
                return AnotherTestableDomainEvent.class;
            }
        });

        assertFalse(this.eventHandled);
        assertFalse(this.anotherEventHandled);

        DomainEventPublisher.instance().publish(new TestableDomainEvent(100L, "test"));

        assertTrue(this.eventHandled);
        assertFalse(this.anotherEventHandled);
    }
}
