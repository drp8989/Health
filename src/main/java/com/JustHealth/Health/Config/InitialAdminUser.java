package com.JustHealth.Health.Config;

import com.JustHealth.Health.Entity.ADMIN_ROLE;
import com.JustHealth.Health.Entity.AdminUser;
import com.JustHealth.Health.Repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
//@RequiredArgsConstructor
//public class InitialAdminUser implements CommandLineRunner {
//
//    private final AdminUserRepository adminUserRepository;
//    private final PasswordEncoder passwordEncoder;
//    @Override
//    public void run(String... args) throws Exception {
//        AdminUser adminUser=new AdminUser();
//        adminUser.setAdminName("Darshan");
//        adminUser.setAdminUserName("drp");
//        adminUser.setAdminEmail("drp@gmail.com");
//        adminUser.setAdminPassword(passwordEncoder.encode("password"));
//        adminUser.setRole(ADMIN_ROLE.valueOf("ROLE_ADMIN"));
//
//        adminUserRepository.save(adminUser);
//
//    }
//}
