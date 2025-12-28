package com.HealthJobsNow.Job.Application.Service;

import java.util.List;

import com.HealthJobsNow.Job.Application.Dto.JobRequest;
import com.HealthJobsNow.Job.Application.Dto.JobResponse;


public interface JobService {
	
	JobResponse createJob(JobRequest request);
	
	JobResponse updateJob(Long jobId, JobRequest request);
	
	void deleteJob(Long jobId);
	
	JobResponse changeJobStatus(Long jobId,String status);

	List<JobResponse> getAllActiveJobs();
	
	List<JobResponse> getAllClosedJobs();
	
	JobResponse getJobById(Long jobId);
	 
	 
	List<JobResponse> getAllJobsByCompany();
	
}
