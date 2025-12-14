package com.HealthJobsNow.Job.Application.Service.Impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.HealthJobsNow.Job.Application.Model.User;
import com.HealthJobsNow.Job.Application.Model.UserRole;
import com.HealthJobsNow.Job.Application.Repository.UserRepository;
import com.HealthJobsNow.Job.Application.Service.UserService;
import com.HealthJobsNow.Job.Application.Utils.AuthUtil;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final AuthUtil authUtil;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepo,
            AuthUtil authUtil,
            PasswordEncoder passwordEncoder) {

        this.userRepo = userRepo;
        this.authUtil = authUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User createUser(User user) {

        if (userRepo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.JOB_SEEKER); 

        return userRepo.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {

        // 🔥 identify user from JWT, not request
        User existingUser = authUtil.loggedInUser();

        existingUser.setName(user.getName());
        existingUser.setPhone(user.getPhone());

        // optional password update
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(
                    passwordEncoder.encode(user.getPassword()));
        }

        return userRepo.save(existingUser);
    }
}
