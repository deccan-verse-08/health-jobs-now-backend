package com.HealthJobsNow.Job.Application.Security;

import com.HealthJobsNow.Job.Application.Model.User;
import com.HealthJobsNow.Job.Application.Model.UserRole;
import com.HealthJobsNow.Job.Application.Repository.UserRepository;
import com.HealthJobsNow.Job.Application.Security.Jwt.AuthEntryPointJwt;
import com.HealthJobsNow.Job.Application.Security.Jwt.AuthTokenFilter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    private AuthEntryPointJwt unautherizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
         
            .cors(cors -> {})
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
               
                .requestMatchers("/api/public/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(unautherizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(httpBasic -> httpBasic.disable())
            .formLogin(form -> form.disable());
        

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {


            if (!userRepository.existsByEmail("admin@healthjobsnow.com")) {
                User admin = User.builder()
                        .name("System Admin")
                        .email("admin@healthjobsnow.com")
                        .password(passwordEncoder.encode("admin123"))
                        .role(UserRole.ADMIN)
                        .build();

                userRepository.save(admin);
            }


            if (!userRepository.existsByEmail("user@healthjobsnow.com")) {
                User user = User.builder()
                        .name("Default User")
                        .email("user@healthjobsnow.com")
                        .password(passwordEncoder.encode("user123"))
                        .role(UserRole.JOB_SEEKER)
                        .build();

                userRepository.save(user);
            }
        };
    }

}