package com.JustHealth.Health.Config;



import com.JustHealth.Health.Service.AdminUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class AppConfig {



    private final AdminUserDetailsService adminUserDetailsService;

//
//    @Autowired
//    DataSource dataSource;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("SELECT admin_username, admin_password, true FROM admin_user WHERE admin_username = ?")
//                .authoritiesByUsernameQuery("SELECT admin_username, role FROM admin_user WHERE admin_username = ?");
//
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
//        manager.setDataSource(dataSource);
//        manager.setUsersByUsernameQuery("SELECT admin_username, adminPassword FROM admin_user WHERE admin_username = ?");
//        manager.setAuthoritiesByUsernameQuery("SELECT admin_username, role FROM admin_user WHERE admin_username = ?");
//        return manager;
//    }


//    @Bean
//    @OnlineOrder(1)
//    public  SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception{
////        http.authorizeHttpRequests(authorize->
////                        authorize.anyRequest().permitAll()
////                                .requestMatchers("api/admin").hasRole("ADMIN")
////
////
////                )
////                .csrf(AbstractHttpConfigurer::disable)
////                .cors(cors->cors.configurationSource(corsConfigurationSource));
//
//        http
//                .securityMatcher(new AntPathRequestMatcher("/api/**"))
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth->auth.anyRequest().authenticated())
//                .userDetailsService(adminUserDetailsService)
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception{
        try {
            http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(authorize->
                                    authorize
//                                            .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                                            .anyRequest().permitAll()
//                                .anyRequest().authenticated()


                    )
                    .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(cors->cors.configurationSource(corsConfigurationSource))
                    .headers(headers->headers
                            .httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable));

            return http.build();
        }catch (Exception e){
            System.out.println(""+e);
            throw e;
        }

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg=new CorsConfiguration();
                cfg.setAllowedOrigins(List.of(
                        "http://localhost:8083"
                ));
                cfg.setAllowedMethods(Collections.singletonList("*"));
                cfg.setAllowCredentials(true);
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setExposedHeaders(List.of("Authorization"));
                cfg.setMaxAge(3600L);
                return cfg;
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
