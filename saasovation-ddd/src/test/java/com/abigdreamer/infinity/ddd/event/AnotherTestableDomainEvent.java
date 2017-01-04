package com.abigdreamer.infinity.ddd.event;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;


public class AnotherTestableDomainEvent implements DomainEvent {

    private int eventVersion;
    private Date occurredOn;
    private double value;

    public AnotherTestableDomainEvent(double aValue) {
        super();

        this.setEventVersion(1);
        this.setOccurredOn(new Date());
        this.setValue(aValue);
    }

    public int eventVersion() {
        return eventVersion;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public double value() {
        return this.value;
    }

    private void setEventVersion(int eventVersion) {
        this.eventVersion = eventVersion;
    }

    private void setOccurredOn(Date occurredOn) {
        this.occurredOn = occurredOn;
    }

    private void setValue(double value) {
        this.value = value;
    }
}
