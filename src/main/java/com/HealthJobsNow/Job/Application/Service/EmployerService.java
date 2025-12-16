package com.HealthJobsNow.Job.Application.Service;

import java.util.List;
import java.util.Map;

import com.HealthJobsNow.Job.Application.Dto.ApplicationResponse;
import com.HealthJobsNow.Job.Application.Dto.EmployerRequest;
import com.HealthJobsNow.Job.Application.Dto.EmployerResponse;

public interface EmployerService {
	    EmployerResponse getProfile();

	    EmployerResponse updateProfile(EmployerRequest request);

	    List<ApplicationResponse> getApplicationsByJob(Long jobId);

	    ApplicationResponse changeApplicationStatus(Long applicationId, String status);

	    Map<String, Long> getApplicationStats();

}
