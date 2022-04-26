package dev.jsoo.gatewayservice.type;

import java.util.Arrays;

public enum AuthType {
    ADMIN("ADMIN"),
    LECTURER("LECTURER"),
    USER("USER"),
    NONE("NONE");

    private String type;
    AuthType(String type) {
        this.type = type;
    }

    public static AuthType getAuthType(String type) {
        return Arrays.stream(AuthType.values()).filter(authType -> authType.type.equals(type.toUpperCase())).findFirst().orElse(NONE);
    }
}
