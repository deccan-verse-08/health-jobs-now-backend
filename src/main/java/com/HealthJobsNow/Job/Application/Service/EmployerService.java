package com.HealthJobsNow.Job.Application.Service;

import java.util.List;
import java.util.Map;

import com.HealthJobsNow.Job.Application.Dto.ApplicationResponse;
import com.HealthJobsNow.Job.Application.Dto.EmployerRequest;
import com.HealthJobsNow.Job.Application.Dto.EmployerResponse;
import com.HealthJobsNow.Job.Application.Model.Application.Status;
import com.HealthJobsNow.Job.Application.Model.Employer;

public interface EmployerService {
	    EmployerResponse getProfile();

	    EmployerResponse createProfile(EmployerRequest request);
	    
	    EmployerResponse updateProfile(EmployerRequest request);
	    
	    EmployerResponse updateStatus(Long id, String status);
	    
	    List<Employer> getAllEmployer();

	    List<ApplicationResponse> getApplicationsByJob(Long jobId);

	    ApplicationResponse changeApplicationStatus(Long applicationId, Status status);

	    Map<String, Long> getApplicationStats();

		

		
		
		
		
		

}
