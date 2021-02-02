package io.ekstrai.simulator;

import java.time.ZonedDateTime;
import java.util.UUID;

public class MachineEvent {
    private final ZonedDateTime timeStamp;
    private final int counter;
    private final String machine;
    private final UUID session;
    private final String paramater;
    private final String value;

    public MachineEvent(ZonedDateTime timeStamp, int counter, String machine, UUID session, String paramater, String value) {
        this.timeStamp = timeStamp;
        this.counter = counter < 0 ?  0 : counter;
        this.machine = machine;
        this.session = session;
        this.paramater = paramater;
        this.value = value;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public int getCounter() {
        return counter;
    }

    public String getMachine() {
        return machine;
    }

    public UUID getSession() {
        return session;
    }

    public String getParamater() {
        return paramater;
    }

    public String getValue() {
        return value;
    }


}
