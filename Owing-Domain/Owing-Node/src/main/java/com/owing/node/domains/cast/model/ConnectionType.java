package com.owing.node.domains.cast.model;

public enum ConnectionType {
    DIRECTIONAL("CONNECTION"), BIDIRECTIONAL("BI_CONNECTION");

    final String value;

    ConnectionType(String value) {
        this.value = value;
    }

//    public static ConnectionType of(String value) {
//        for (ConnectionType type : ConnectionType.values()) {
//            if (type.value.equalsIgnoreCase(value)) {
//                return type;
//            }
//        }
//        throw CastingException.of(CastingErrorCode.ILLEGAL_TYPE_ARGS);
//    }
}
