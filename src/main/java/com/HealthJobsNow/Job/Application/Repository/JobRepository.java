package com.HealthJobsNow.Job.Application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HealthJobsNow.Job.Application.Model.Job;

public interface JobRepository extends JpaRepository<Job, Long>{

}
