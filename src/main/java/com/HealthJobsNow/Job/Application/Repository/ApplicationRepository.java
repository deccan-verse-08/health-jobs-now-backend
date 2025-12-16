package com.HealthJobsNow.Job.Application.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HealthJobsNow.Job.Application.Model.Application;
import com.HealthJobsNow.Job.Application.Model.Company;
import com.HealthJobsNow.Job.Application.Model.Job;
import com.HealthJobsNow.Job.Application.Model.JobSeeker;

public interface ApplicationRepository extends JpaRepository<Application, Long>{

	List<Application> findByJobSeeker(JobSeeker jobSeeker);

	boolean existsByJobSeekerAndJob(JobSeeker jobSeeker, Job job);
	
	List<Application> findByJob(Job job);

	List<Application> findByJob_Company(Company company);

}
