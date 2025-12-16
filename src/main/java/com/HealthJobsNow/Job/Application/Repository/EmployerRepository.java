package com.HealthJobsNow.Job.Application.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HealthJobsNow.Job.Application.Model.Employer;
import com.HealthJobsNow.Job.Application.Model.User;

public interface EmployerRepository extends JpaRepository<Employer, Long>{

	Optional<Employer> findByUser(User loggedInUser);

}
