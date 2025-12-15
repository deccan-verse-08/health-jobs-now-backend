package com.HealthJobsNow.Job.Application.Service;

import java.util.List;
import java.util.Map;

import com.HealthJobsNow.Job.Application.Dto.JobSeekerRequest;
import com.HealthJobsNow.Job.Application.Dto.JobSeekerResponse;

public interface JobSeekerService {
	
	
	JobSeekerResponse getProfile();
	
	JobSeekerResponse updateProfile(JobSeekerRequest request);
	

	List<Map<String, Object>> getApplications();
	
	
	 Map<String, Object> applyJob(Long jobId);
	 
	 
	 Map<String, Long> getApplicationCount();
	 
	 Integer getProfileCompleteness();

}
