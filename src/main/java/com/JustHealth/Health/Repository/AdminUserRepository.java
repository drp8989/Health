package com.JustHealth.Health.Repository;

import com.JustHealth.Health.Entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser,Long> {

    AdminUser findByAdminUserName(String username);

}
