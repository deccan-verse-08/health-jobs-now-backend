package com.HealthJobsNow.Job.Application.Utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.HealthJobsNow.Job.Application.Model.User;
import com.HealthJobsNow.Job.Application.Repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final UserRepository userRepository;

    private String getAuthenticatedEmail() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
            authentication instanceof AnonymousAuthenticationToken) {
            throw new AccessDeniedException("Unauthenticated request");
        }

        return authentication.getName(); // email
    }

    public User loggedInUser() {
        return userRepository.findByEmail(getAuthenticatedEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
    }

    public Long loggedInUserId() {
        return loggedInUser().getId();
    }
}