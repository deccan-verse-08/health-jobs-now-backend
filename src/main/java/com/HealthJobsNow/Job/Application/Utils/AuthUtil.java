package com.HealthJobsNow.Job.Application.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.HealthJobsNow.Job.Application.Model.User;
import com.HealthJobsNow.Job.Application.Repository.UserRepository;
@Component
public class AuthUtil {

    @Autowired
    private UserRepository userRepository;

    private String getAuthenticatedEmail() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Unauthenticated request");
        }

        return authentication.getName(); // EMAIL
    }

    public Long loggedInUserId() {
        return userRepository.findByEmail(getAuthenticatedEmail())
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();   // ✅ correct field
    }

    public User loggedInUser() {
        return userRepository.findByEmail(getAuthenticatedEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
