package com.abigdreamer.infinity.ddd.event;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;


public class TestableNavigableDomainEvent implements DomainEvent {

    private int eventVersion;
    
    private TestableDomainEvent nestedEvent;
    private Date occurredOn;

    public TestableNavigableDomainEvent(long anId, String aName) {
        super();

        this.setEventVersion(1);
        this.setNestedEvent(new TestableDomainEvent(anId, aName));
        this.setOccurredOn(new Date());
    }

    public int eventVersion() {
        return eventVersion;
    }

    public TestableDomainEvent nestedEvent() {
        return nestedEvent;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    private void setEventVersion(int eventVersion) {
        this.eventVersion = eventVersion;
    }

    private void setNestedEvent(TestableDomainEvent nestedEvent) {
        this.nestedEvent = nestedEvent;
    }

    private void setOccurredOn(Date occurredOn) {
        this.occurredOn = occurredOn;
    }
}
