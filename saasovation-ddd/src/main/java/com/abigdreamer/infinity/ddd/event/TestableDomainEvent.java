package com.abigdreamer.infinity.ddd.event;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;


public class TestableDomainEvent implements DomainEvent {

	private long id;
    private String name;
    
    private int eventVersion;
    private Date occurredOn;

    public TestableDomainEvent(long anId, String aName) {
        super();

        this.setEventVersion(1);
        this.setId(anId);
        this.setName(aName);
        this.setOccurredOn(new Date());
    }

    public int eventVersion() {
        return eventVersion;
    }

    public long id() {
        return id;
    }

    public String name() {
        return name;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    private void setEventVersion(int anEventVersion) {
        this.eventVersion = anEventVersion;
    }

    private void setId(long id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setOccurredOn(Date occurredOn) {
        this.occurredOn = occurredOn;
    }
}
