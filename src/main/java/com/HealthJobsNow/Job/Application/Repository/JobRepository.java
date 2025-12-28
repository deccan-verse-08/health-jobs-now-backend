package com.HealthJobsNow.Job.Application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HealthJobsNow.Job.Application.Model.Company;
import com.HealthJobsNow.Job.Application.Model.Job;
import java.util.*;
public interface JobRepository extends JpaRepository<Job, Long>{
	
	 List<Job> findByStatus(Job.JobStatus status);
	 
	 List<Job> findByStatusAndMinExperienceMonthsLessThanEqual(
	            Job.JobStatus status,
	            Integer experienceMonths
	    );
	 List<Job> findByStatusAndTitleContainingIgnoreCase(
	            Job.JobStatus status,
	            String keyword
	    );
	 
	 List<Job> findByCompany(Company company);
	 
	 

}
