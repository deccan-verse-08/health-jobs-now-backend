package com.HealthJobsNow.Job.Application.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.HealthJobsNow.Job.Application.Dto.JobResponse;
import com.HealthJobsNow.Job.Application.Service.JobService;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/job")
public class JobController {
	
	
	private final JobService jobService;

	@GetMapping
	public ResponseEntity<?> getAllActiveJobs(){
		return ResponseEntity.ok(jobService.getAllActiveJobs());
	}
	
	@GetMapping("/{jobId}")
	public ResponseEntity<?> getJobById(@PathVariable Long jobId){
	   JobResponse response = jobService.getJobById(jobId);
	   return ResponseEntity.ok(response);
	}
	
	@GetMapping("/employer")
	public ResponseEntity<?> getJobsByCompany(){
		List<JobResponse> response = jobService.getAllJobsByCompany();
		return ResponseEntity.ok(response);
	}
	
	
	

	
}
