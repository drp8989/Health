package com.JustHealth.Health.Service;


import com.JustHealth.Health.Config.JwtProvider;
import com.JustHealth.Health.Entity.AdminUser;
import com.JustHealth.Health.Repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class AdminUserService {


    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AdminUserRepository adminUserRepository;

    public AdminUser getUserFromJwtToken(String jwt){
        String username=jwtProvider.getEmailFromJwtToken(jwt);
        AdminUser adminUser=adminUserRepository.findByAdminUserName(username);
        return adminUser;
    }

}
