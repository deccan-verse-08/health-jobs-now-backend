package com.HealthJobsNow.Job.Application.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HealthJobsNow.Job.Application.Dto.JobRequest;
import com.HealthJobsNow.Job.Application.Dto.JobResponse;
import com.HealthJobsNow.Job.Application.Service.JobService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/job")
public class JobController {
	
	private final JobService jobService;
	
	
	@PostMapping("/employer")
	public ResponseEntity<JobResponse> createJob(@Valid @RequestBody JobRequest request){
		
		JobResponse job = jobService.createJob(request);
		return ResponseEntity.ok(job);
	}
	
	@PutMapping("/employer/{jobId}")
	public ResponseEntity<JobResponse> updateJob(@Valid @RequestBody JobRequest request , @PathVariable Long jobId){
		JobResponse job = jobService.updateJob(jobId, request);
		return ResponseEntity.ok(job);
	}
	@PutMapping("/employer/{jobId}/status/{status}")
	public ResponseEntity<JobResponse> changeJobStatus(
	        @PathVariable Long jobId,
	        @PathVariable String status) {

	    return ResponseEntity.ok(jobService.changeJobStatus(jobId, status));
	}
	@DeleteMapping("/employer/{jobId}")
	public ResponseEntity<String> deleteJob(@PathVariable Long jobId){
		 jobService.deleteJob(jobId);
		 return ResponseEntity.ok("Job Deleted");
	}
	
	
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
