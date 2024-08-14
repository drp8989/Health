package com.JustHealth.Health.Config;


import com.JustHealth.Health.Entity.AdminUser;
import com.JustHealth.Health.Repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

//@RequiredArgsConstructor
//@Component
//@Slf4j
//public class InitialUserInfo implements CommandLineRunner {
//    private final AdminUserRepository adminUserRepository;
//    private final PasswordEncoder passwordEncoder;
//    @Override
//    public void run(String... args) throws Exception {
//        AdminUser adminUser=new AdminUser();
//        adminUser.setAdminName("Manager");
//        adminUser.setAdminUserName("Manager");
//        adminUser.setAdminPassword(passwordEncoder.encode("password"));
//        adminUser.setRoles("ROLE_MANAGER");
//        adminUser.setAdminEmail("manager@manager.com");
//
//        AdminUser adminUser1=new AdminUser();
//        adminUser.setAdminName("Admin");
//        adminUser.setAdminUserName("Admin");
//        adminUser.setAdminPassword(passwordEncoder.encode("password"));
//        adminUser.setRoles("ROLE_ADMIN");
//        adminUser.setAdminEmail("manager@manager.com");
//
//        adminUserRepository.saveAll(List.of(adminUser,adminUser1));
//
//    }
//}
