package com.HealthJobsNow.Job.Application.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HealthJobsNow.Job.Application.Model.JobSeeker;
import com.HealthJobsNow.Job.Application.Model.User;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {
	Optional<JobSeeker> findByUser(User user);

}
