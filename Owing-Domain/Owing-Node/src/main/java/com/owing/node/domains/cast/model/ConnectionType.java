package com.owing.node.domains.cast.model;

import com.owing.node.domains.cast.error.code.CastNodeErrorCode;
import com.owing.node.domains.cast.error.exception.CastException;

public enum ConnectionType {
    DIRECTIONAL("CONNECTION"), BIDIRECTIONAL("BI_CONNECTION");

    final String value;

    ConnectionType(String value) {
        this.value = value;
    }

    public static ConnectionType of(String value) {
        for (ConnectionType type : ConnectionType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw CastException.of(CastNodeErrorCode.ILLEGAL_HANDLE_ARGS);
    }
}
