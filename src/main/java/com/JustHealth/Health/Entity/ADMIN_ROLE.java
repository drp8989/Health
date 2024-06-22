package com.JustHealth.Health.Entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.JustHealth.Health.Entity.Permissions.*;


@RequiredArgsConstructor
public enum ADMIN_ROLE {

//    ROLE_ADMIN,
//    ROLE_PHARMACIST

    ROLE_ADMIN(Set.of(
            ADMIN_CREATE,
            ADMIN_READ,
            ADMIN_DELETE,
            ADMIN_UPDATE,
            PHARMACIST_CREATE,
            PHARMACIST_READ,
            PHARMACIST_UPDATE,
            PHARMACIST_DELETE
    )),
    ROLE_PHARMACIST(Set.of(
            PHARMACIST_CREATE,
            PHARMACIST_READ,
            PHARMACIST_UPDATE,
            PHARMACIST_DELETE
    )),
    ROLE_DOCTOR(Collections.emptySet());

    @Getter
    private final Set<Permissions> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority( this.name()));
        return authorities;
    }




}



