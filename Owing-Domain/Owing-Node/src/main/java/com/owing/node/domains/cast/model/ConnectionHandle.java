package com.owing.node.domains.cast.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.owing.node.domains.cast.error.code.CastNodeErrorCode;
import com.owing.node.domains.cast.error.exception.CastException;

public enum ConnectionHandle {
    TOP("top"), BOTTOM("bottom"), RIGHT("right"), LEFT("left");

    final String value;

    ConnectionHandle(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ConnectionHandle fromString(String value) {
        value = value.toLowerCase();
        for (ConnectionHandle handle : ConnectionHandle.values()) {
            if (handle.value.equals(value)) {
                return handle;
            }
        }
        throw CastException.of(CastNodeErrorCode.ILLEGAL_HANDLE_ARGS);
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }
}
