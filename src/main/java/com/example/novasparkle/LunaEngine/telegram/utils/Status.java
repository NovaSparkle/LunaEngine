package com.example.novasparkle.LunaEngine.telegram.utils;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

@Getter
public enum Status {

    DEVELOPER(1, Permission.WRITE_PLUGIN),
    CURATOR(2, Permission.WRITE_KEY, Permission.SHOW_USERS),
    ADMIN(3, Permission.REGISTER_USER),
    CLIENT(0, Permission.GET_KEY, Permission.USE);
    private final int weight;
    private final Set<Permission> permissions;
    Status(int weight, Permission... permissions) {
        this.weight = weight;
        this.permissions = Set.of(permissions);
    }
    public boolean hasPermission(Permission permission) {
        if (this.equals(ADMIN)) return true;
        return Arrays.stream(Status.values()).filter(status -> status.getWeight() <= this.getWeight()).anyMatch(status -> status.hasPermission(permission));
    }
}
