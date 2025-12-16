package com.HealthJobsNow.Job.Application.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.HealthJobsNow.Job.Application.Dto.ApplicationResponse;
import com.HealthJobsNow.Job.Application.Dto.EmployerRequest;
import com.HealthJobsNow.Job.Application.Dto.EmployerResponse;
import com.HealthJobsNow.Job.Application.Service.EmployerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employer")
public class EmployerController {

    private final EmployerService employerService;

    // ---------------- PROFILE ----------------

    @GetMapping("/profile")
    public ResponseEntity<EmployerResponse> getProfile() {
        return ResponseEntity.ok(employerService.getProfile());
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
            @RequestParam String status) {
        return ResponseEntity.ok(
                employerService.changeApplicationStatus(applicationId, status)
        );
    }

    // ---------------- STATS ----------------

    @GetMapping("/applications/stats")
    public ResponseEntity<Map<String, Long>> getApplicationStats() {
        return ResponseEntity.ok(
                employerService.getApplicationStats()
        );
    }
}