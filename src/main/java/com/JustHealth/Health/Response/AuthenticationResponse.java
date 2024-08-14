package com.JustHealth.Health.Response;

import com.JustHealth.Health.Entity.ADMIN_ROLE;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private String message;
    private ADMIN_ROLE role;

}
