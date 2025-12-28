package com.HealthJobsNow.Job.Application.Service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.HealthJobsNow.Job.Application.Dto.JobRequest;
import com.HealthJobsNow.Job.Application.Dto.JobResponse;
import com.HealthJobsNow.Job.Application.Model.Company;
import com.HealthJobsNow.Job.Application.Model.CompanyStatus;
import com.HealthJobsNow.Job.Application.Model.Employer;
import com.HealthJobsNow.Job.Application.Model.EmployerStatus;
import com.HealthJobsNow.Job.Application.Model.Job;
import com.HealthJobsNow.Job.Application.Model.Job.JobStatus;
import com.HealthJobsNow.Job.Application.Repository.EmployerRepository;
import com.HealthJobsNow.Job.Application.Repository.JobRepository;
import com.HealthJobsNow.Job.Application.Service.JobService;
import com.HealthJobsNow.Job.Application.Utils.AuthUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;
    private final AuthUtil utils;

    // ---------------- CREATE JOB ----------------

    @Override
    public JobResponse createJob(JobRequest request) {

        Employer employer = validateEmployerForJobActions();

        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .skillsRequired(request.getSkillsRequired())
                .minExperienceMonths(request.getMinExperienceMonths())
                .jobType(Job.JobType.valueOf(request.getJobType().toUpperCase()))
                .status(Job.JobStatus.ACTIVE)
                .company(employer.getCompany())
                .build();

        return mapToResponse(jobRepository.save(job));
    }

    // ---------------- UPDATE JOB ----------------

    @Override
    public JobResponse updateJob(Long jobId, JobRequest request) {

        Employer employer = validateEmployerForJobActions();

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getCompany().getId().equals(employer.getCompany().getId())) {
            throw new RuntimeException("Unauthorized to update this job");
        }

        if (job.getStatus() == Job.JobStatus.CLOSED) {
            throw new RuntimeException("Cannot update a closed job");
        }

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setSkillsRequired(request.getSkillsRequired());
        job.setMinExperienceMonths(request.getMinExperienceMonths());
        job.setJobType(Job.JobType.valueOf(request.getJobType().toUpperCase()));

        return mapToResponse(jobRepository.save(job));
    }

    // ---------------- SOFT DELETE JOB ----------------

    @Override
    public void deleteJob(Long jobId) {

        Employer employer = validateEmployerForJobActions();

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getCompany().getId().equals(employer.getCompany().getId())) {
            throw new RuntimeException("Unauthorized to delete this job");
        }

        job.setStatus(Job.JobStatus.CLOSED);
        jobRepository.save(job);
    }

    // ---------------- CHANGE JOB STATUS ----------------

    @Override
    public JobResponse changeJobStatus(Long jobId, String status) {

        Employer employer = validateEmployerForJobActions();

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getCompany().getId().equals(employer.getCompany().getId())) {
            throw new RuntimeException("Unauthorized");
        }

        job.setStatus(Job.JobStatus.valueOf(status.toUpperCase()));
        return mapToResponse(jobRepository.save(job));
    }

    // ---------------- FETCH JOBS ----------------

    @Override
    public List<JobResponse> getAllActiveJobs() {
        return jobRepository.findByStatus(Job.JobStatus.ACTIVE)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<JobResponse> getAllClosedJobs() {
        return jobRepository.findByStatus(JobStatus.CLOSED)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public JobResponse getJobById(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("No Job Found"));
        return mapToResponse(job);
    }

    @Override
    public List<JobResponse> getAllJobsByCompany() {

        Employer employer = getLoggedInEmployer();

        if (employer.getCompany() == null) {
            throw new RuntimeException("Employer is not associated with any company");
        }

        return jobRepository.findByCompany(employer.getCompany())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ---------------- HELPERS ----------------

    private Employer getLoggedInEmployer() {
        return employerRepository.findByUser(utils.loggedInUser())
                .orElseThrow(() -> new RuntimeException("Employer not found"));
    }

    private Employer validateEmployerForJobActions() {

        Employer employer = getLoggedInEmployer();

        if (employer.getCompany() == null) {
            throw new RuntimeException("Employer is not associated with any company");
        }

        if (employer.getStatus() != EmployerStatus.APPROVED) {
            throw new RuntimeException("Employer is not approved");
        }

        Company company = employer.getCompany();

        if (company.getStatus() != CompanyStatus.VERIFIED) {
            throw new RuntimeException("Company is not verified");
        }

        return employer;
    }

    private JobResponse mapToResponse(Job job) {

        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .location(job.getLocation())
                .skillsRequired(job.getSkillsRequired())
                .minExperienceMonths(job.getMinExperienceMonths())
                .jobType(job.getJobType().name())
                .status(job.getStatus().name())
                .companyName(job.getCompany().getName())
                .createdAt(job.getCreatedAt())
                .build();
    }
}
