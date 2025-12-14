package com.HealthJobsNow.Job.Application.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HealthJobsNow.Job.Application.Model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);

}
