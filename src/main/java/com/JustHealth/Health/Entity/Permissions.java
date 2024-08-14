package com.JustHealth.Health.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permissions {


    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),



    PHARMACIST_READ("pharmacist:read"),
    PHARMACIST_UPDATE("pharmacist:update"),
    PHARMACIST_CREATE("pharmacist:create"),
    PHARMACIST_DELETE("pharmacist:delete");



    @Getter
    private final String permission;


}
