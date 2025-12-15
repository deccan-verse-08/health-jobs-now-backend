package com.HealthJobsNow.Job.Application.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HealthJobsNow.Job.Application.Dto.JobSeekerRequest;
import com.HealthJobsNow.Job.Application.Dto.JobSeekerResponse;
import com.HealthJobsNow.Job.Application.Service.JobSeekerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/job-seeker")
@RequiredArgsConstructor
public class JobSeekerController {
	
	
	private final JobSeekerService jobSeekerService;
	
	@GetMapping("/profile")
	public ResponseEntity<JobSeekerResponse> getProfile(){
		JobSeekerResponse response = jobSeekerService.getProfile();
		return ResponseEntity.ok(response);
	}
	
	
	@PutMapping("/profile")
	public ResponseEntity<JobSeekerResponse> updateProfile(@Valid @RequestBody JobSeekerRequest request){
		JobSeekerResponse response = jobSeekerService.updateProfile(request);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/applications")
	public ResponseEntity<List<Map<String, Object>>> getApplications(){
		List<Map<String, Object>> applications = jobSeekerService.getApplications();
		return ResponseEntity.ok(applications);
		
	}
	
	@PostMapping("/applications/{jobId}/apply")
	public ResponseEntity<Map<String, Object>> applyJob(@PathVariable Long jobId){
		Map<String, Object> response = jobSeekerService.applyJob(jobId);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/applications/count")
	public ResponseEntity<Map<String, Long>> getApplicationCount(){
		Map<String, Long> count = jobSeekerService.getApplicationCount();
		return ResponseEntity.ok(count);
	}
	
	
	@GetMapping("/profile/completeness")
	public ResponseEntity<Integer> getProfileCompleteness(){
		Integer completeness = jobSeekerService.getProfileCompleteness();
		return ResponseEntity.ok(completeness);
	}

}
