package com.JustHealth.Health.Controller;


import com.JustHealth.Health.Config.JwtProvider;
import com.JustHealth.Health.DTO.AdminDTO;
import com.JustHealth.Health.DTO.AdminLoginDTO;
import com.JustHealth.Health.Entity.ADMIN_ROLE;
import com.JustHealth.Health.Entity.AdminUser;
import com.JustHealth.Health.Repository.AdminUserRepository;
import com.JustHealth.Health.Response.AuthenticationResponse;
import com.JustHealth.Health.Service.AdminUserDetailsService;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    private AdminUserDetailsService adminUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;




    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signin(@RequestBody AdminLoginDTO req){

        try {
            String username = req.getUsername();
            String password = req.getPassword();

            // Check if username or password is null or empty
            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            try {
                // Authenticate the user
                Authentication authentication = authenticate(username, password);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Get user roles
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
                if (role == null) {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }

                // Generate JWT token
                String jwt = jwtProvider.generateToken(authentication);

                // Prepare response
                AuthenticationResponse authenticationResponse = new AuthenticationResponse();
                authenticationResponse.setJwt(jwt);
                authenticationResponse.setRole(ADMIN_ROLE.valueOf(role));
                authenticationResponse.setMessage("Login Success");

                return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
            } catch (BadCredentialsException e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } catch (UsernameNotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Authentication authenticate(String username,String password){
        UserDetails userDetails=adminUserDetailsService.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid Username");
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }
}
