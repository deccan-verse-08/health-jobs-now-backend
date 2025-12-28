package com.HealthJobsNow.Job.Application.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HealthJobsNow.Job.Application.Dto.ApplicationResponse;
import com.HealthJobsNow.Job.Application.Dto.ApplicationStatusRequest;
import com.HealthJobsNow.Job.Application.Dto.EmployerRequest;
import com.HealthJobsNow.Job.Application.Dto.EmployerResponse;
import com.HealthJobsNow.Job.Application.Dto.JobRequest;
import com.HealthJobsNow.Job.Application.Dto.JobResponse;
import com.HealthJobsNow.Job.Application.Service.EmployerService;
import com.HealthJobsNow.Job.Application.Service.JobService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employer")
public class EmployerController {

    private final EmployerService employerService;
    
    private final JobService jobService;

    // ---------------- PROFILE ----------------

    @GetMapping("/profile")
    public ResponseEntity<EmployerResponse> getProfile() {
        return ResponseEntity.ok(employerService.getProfile());
    }

    @PostMapping("/profile")
    public ResponseEntity<EmployerResponse> createProfile(
            @Valid @RequestBody EmployerRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employerService.createProfile(request));
    }

    @PutMapping("/profile")
    public ResponseEntity<EmployerResponse> updateProfile(
            @Valid @RequestBody EmployerRequest request) {

        return ResponseEntity.ok(employerService.updateProfile(request));
    }

    // ---------------- APPLICATIONS ----------------

    @GetMapping("/jobs/{jobId}/applications")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByJob(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                employerService.getApplicationsByJob(jobId)
        );
    }

    @PatchMapping("/applications/{applicationId}/status")
    public ResponseEntity<ApplicationResponse> changeApplicationStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody ApplicationStatusRequest request) {

        return ResponseEntity.ok(
                employerService.changeApplicationStatus(
                        applicationId,
                        request.getStatus()
                )
        );
    }

    // ---------------- STATS ----------------

    @GetMapping("/applications/stats")
    public ResponseEntity<Map<String, Long>> getApplicationStats() {
        return ResponseEntity.ok(
                employerService.getApplicationStats()
        );
    }
    
    
    //---------------------Create Jobs ---------------------
	@PostMapping("/job")
	public ResponseEntity<JobResponse> createJob(@Valid @RequestBody JobRequest request){
		
		JobResponse job = jobService.createJob(request);
		return ResponseEntity.ok(job);
	}
	
	@PutMapping("/job/{jobId}")
	public ResponseEntity<JobResponse> updateJob(@Valid @RequestBody JobRequest request , @PathVariable Long jobId){
		JobResponse job = jobService.updateJob(jobId, request);
		return ResponseEntity.ok(job);
	}
		
	@PutMapping("/{jobId}/status/{status}")
	public ResponseEntity<JobResponse> changeJobStatus(
	        @PathVariable Long jobId,
	        @PathVariable String status) {

	    return ResponseEntity.ok(jobService.changeJobStatus(jobId, status));
	}
	
	@DeleteMapping("/{jobId}")
	public ResponseEntity<String> deleteJob(@PathVariable Long jobId){
		 jobService.deleteJob(jobId);
		 return ResponseEntity.ok("Job Deleted");
	}
    
}
