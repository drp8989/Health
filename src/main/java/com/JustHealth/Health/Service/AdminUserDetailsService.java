package com.JustHealth.Health.Service;

import com.JustHealth.Health.Entity.AdminUser;
import com.JustHealth.Health.Repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    AdminUserRepository adminUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AdminUser adminUser=adminUserRepository.findByAdminUserName(username);

        if(adminUser==null){
            throw new UsernameNotFoundException("username"+username+"is not found");
        }

//        String role= String.valueOf(adminUser.getRole());
//        String role=adminUser.getRole().toString();
//        String permisions=adminUser.getRole().getPermissions().toString();
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(adminUser.getRole().getAuthorities().toString()));
//        System.out.println(authorities+"from load by username");
//        authorities.add(new SimpleGrantedAuthority(role));
//        authorities.add(new SimpleGrantedAuthority(permisions));

        return new org.springframework.security.core.userdetails.User(adminUser.getAdminUserName(),adminUser.getAdminPassword(),adminUser.getRole().getAuthorities());
//        return new org.springframework.security.core.userdetails.User(adminUser.getAdminEmail(), adminUser.getAdminPassword(),authorities);

    }
}
