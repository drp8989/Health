package com.JustHealth.Health.Controller;


import com.JustHealth.Health.Config.JwtConstant;
import com.JustHealth.Health.Entity.AdminUser;
import com.JustHealth.Health.Service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashboardController {


    @Autowired
    AdminUserService adminUserService;

//    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_ADMIN','ROLE_USER')")
//    @GetMapping("/welcome-message")
//    public ResponseEntity<String> getFirstWelcomeMessage(Authentication authentication){
////        return ResponseEntity.ok("Welcome to the JWT Tutorial:"+authentication.get()+"with scope:"+authentication.getAuthorities());
//    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/manager-message")
    public ResponseEntity<String> getManagerData(Principal principal){
        return ResponseEntity.ok("Manager::"+principal.getName());

    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PostMapping("/admin-message")
//    public ResponseEntity<AdminUser> getAdminData(@RequestHeader(JwtConstant.JWT_HEADER) String jwt, @RequestParam("message") String message, Principal principal){
//        AdminUser adminUser=adminUserService.getUserFromJwtToken(jwt);
//        return ResponseEntity<AdminUser>(adminUser, HttpStatus.OK);
////        return ResponseEntity.ok("Admin::"+principal.getName()+" has this message:"+message);
//
//    }

//    @PreAuthorize("hasRole('ROLE_Manager')")
//    @PostMapping("/admin-message")
//    public ResponseEntity<AdminUser> getAdminData(
//            @RequestHeader(JwtConstant.JWT_HEADER) String jwt,
//            @RequestParam("message") String message,
//            Principal principal) {
//
//        AdminUser adminUser = adminUserService.getUserFromJwtToken(jwt);
//
//        // You can return the adminUser directly as ResponseEntity
//        return ResponseEntity.ok(adminUser);
//
//    }


    @PreAuthorize("hasAuthority('admin:read')")
    @PostMapping("/admin-message")
    public ResponseEntity<AdminUser> getAdminData(
            @RequestHeader(JwtConstant.JWT_HEADER) String jwt,
            @RequestParam("message") String message,
            Principal principal) {

        AdminUser adminUser = adminUserService.getUserFromJwtToken(jwt);


        // You can return the adminUser directly as ResponseEntity
        return ResponseEntity.ok(adminUser);

    }


}
