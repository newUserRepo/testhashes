package com.example.demo.util;

import java.time.Duration;
import java.time.Instant;

public class TimeCount {

    private Instant initTime;
    private Instant endTime;

    public void init() {
        this.initTime = Instant.now();
    }

    public void endTime() {
        this.endTime = Instant.now();
    }

    public Long getTimeMillis() {
        return Duration.between(initTime , endTime).toMillis();
    }

    public Long getTimeSec() {
        return getTimeMillis() / 1000;
    }
}
