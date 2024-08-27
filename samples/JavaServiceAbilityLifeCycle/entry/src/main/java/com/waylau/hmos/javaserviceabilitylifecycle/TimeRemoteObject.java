package com.waylau.hmos.javaserviceabilitylifecycle;

import ohos.aafwk.ability.LocalRemoteObject;

import java.time.LocalDateTime;

public class TimeRemoteObject extends LocalRemoteObject {
    private LocalDateTime time;

    public TimeRemoteObject() {
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime getTime() {
        return time;
    }
}