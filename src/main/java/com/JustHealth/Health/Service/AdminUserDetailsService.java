package com.JustHealth.Health.Service;

import com.JustHealth.Health.Entity.AdminUser;
import com.JustHealth.Health.Repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    AdminUserRepository adminUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser adminUser=adminUserRepository.findByAdminUserName(username);
        if(adminUser==null){
            throw new UsernameNotFoundException("username"+username+"is not found");
        }

        String role= String.valueOf(adminUser.getRole());
        List<GrantedAuthority> authorities=new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role));
        return new org.springframework.security.core.userdetails.User(adminUser.getAdminEmail(), adminUser.getAdminPassword(),authorities);

    }
}
