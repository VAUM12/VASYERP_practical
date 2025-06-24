package com.practical.demo.user.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleType {
    ADMIN,
    USER;

    @JsonValue
    public String toRoleName() {
        return "ROLE_" + this.name();
    }
}

