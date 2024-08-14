package com.JustHealth.Health.Service;

import com.JustHealth.Health.Entity.ADMIN_ROLE;
import com.JustHealth.Health.Entity.AdminUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@RequiredArgsConstructor
public class AdminUserDetails implements UserDetails {


    @Autowired
    private final AdminUser adminUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return adminUser.getRole().getAuthorities();

//        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
//
//        // Add role authority
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + adminUser.getRole().name()));
//
//        // Add permissions associated with the role
//        ADMIN_ROLE role = adminUser.getRole();
//        role.getPermissions().forEach(permission -> {
//            authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
//        });

//        return authorities;
    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Arrays.stream(adminUser.getRole())
//                .map(SimpleGrantedAuthority::new)
//                .toList();
//    }

    @Override
    public String getPassword() {
        return adminUser.getAdminPassword();
    }

    @Override
    public String getUsername() {
        return adminUser.getAdminUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
