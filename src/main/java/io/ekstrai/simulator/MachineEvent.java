package io.ekstrai.simulator;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonRawValue;

import java.time.OffsetDateTime;
import java.util.UUID;

public class MachineEvent {

    private static final String DEFAULT_ZONE = "Turkey";

    private final OffsetDateTime timeStamp;
    private final String machine;
    private final int counter;
    private final UUID session;
    private final String parameter;
    private final String value;

    public MachineEvent(OffsetDateTime timeStamp, String machine, int counter, UUID session, String parameter, String value) {
        this.timeStamp = timeStamp;
        this.machine = machine;
        this.counter = counter < 0 ?  0 : counter;
        this.session = session;
        this.parameter = parameter;
        this.value = value;
    }

    public MachineEvent(String machine, int counter, String parameter, String value) {
        this.timeStamp = OffsetDateTime.now();
        this.machine = machine;
        this.counter = counter < 0 ?  0 : counter;
        this.session = UUID.randomUUID();
        this.parameter = parameter;
        this.value = value;
    }

    @JsonGetter
    @JsonRawValue
    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }

    @JsonGetter
    public String getMachine() {
        return machine;
    }

    @JsonGetter
    public int getCounter() {
        return counter;
    }

    @JsonGetter
    public UUID getSession() {
        return session;
    }

    @JsonGetter
    public String getParameter() {
        return parameter;
    }

    @JsonGetter
    public String getValue() {
        return value;
    }


}
