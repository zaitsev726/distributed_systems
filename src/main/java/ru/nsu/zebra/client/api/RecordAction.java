package ru.nsu.zebra.client.api;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RecordAction {
    UPDATE("update"),
    DELETE("delete");

    public final String action;

    RecordAction(String action) {
        this.action = action;
    }

    @JsonValue
    public String getAction() {
        return action;
    }
}
