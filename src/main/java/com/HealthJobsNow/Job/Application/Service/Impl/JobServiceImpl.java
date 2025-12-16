package com.HealthJobsNow.Job.Application.Service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.HealthJobsNow.Job.Application.Dto.JobRequest;
import com.HealthJobsNow.Job.Application.Dto.JobResponse;
import com.HealthJobsNow.Job.Application.Model.Company;
import com.HealthJobsNow.Job.Application.Model.Employer;
import com.HealthJobsNow.Job.Application.Model.Job;
import com.HealthJobsNow.Job.Application.Repository.EmployerRepository;
import com.HealthJobsNow.Job.Application.Repository.JobRepository;
import com.HealthJobsNow.Job.Application.Service.JobService;
import com.HealthJobsNow.Job.Application.Utils.AuthUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements JobService{
	
	private final JobRepository jobRepository;
    private final AuthUtil utils;
    private final EmployerRepository employerRepository;

	@Override
	public JobResponse createJob(JobRequest request) {
		Employer employer = getLoggedInEmployer();
		
		Company company = employer.getCompany();
		
		 Job job = Job.builder()
		            .title(request.getTitle())
		            .description(request.getDescription())
		            .location(request.getLocation())
		            .skillsRequired(request.getSkillsRequired())
		            .minExperienceMonths(request.getMinExperienceMonths())
		            .jobType(Job.JobType.valueOf(request.getJobType()))
		            .status(Job.JobStatus.ACTIVE)
		            .company(company)
		            .build();
		 Job savedJob = jobRepository.save(job);
		 return mapToResponse(savedJob);		
	}

	@Override
	public JobResponse updateJob(Long jobId, JobRequest request) {
		Employer employer = getLoggedInEmployer();

	    Job job = jobRepository.findById(jobId)
	            .orElseThrow(() -> new RuntimeException("Job not found"));
	    if (!job.getCompany().getId().equals(employer.getCompany().getId())) {
	        throw new RuntimeException("Unauthorized to update this job");
	    }
	    job.setTitle(request.getTitle());
	    job.setDescription(request.getDescription());
	    job.setLocation(request.getLocation());
	    job.setSkillsRequired(request.getSkillsRequired());
	    job.setMinExperienceMonths(request.getMinExperienceMonths());
	    job.setJobType(Job.JobType.valueOf(request.getJobType()));
	    Job updatedJob = jobRepository.save(job);
	    
		return mapToResponse(updatedJob);
	}

	@Override
	public void deleteJob(Long jobId) {
		 Employer employer = getLoggedInEmployer();

		    Job job = jobRepository.findById(jobId)
		            .orElseThrow(() -> new RuntimeException("Job not found"));

		    if (!job.getCompany().getId().equals(employer.getCompany().getId())) {
		        throw new RuntimeException("Unauthorized to delete this job");
		    }

		    jobRepository.delete(job);
		
	}

	@Override
	public JobResponse changeJobStatus(Long jobId, String status) {
		Employer employer = getLoggedInEmployer();

	    Job job = jobRepository.findById(jobId)
	            .orElseThrow(() -> new RuntimeException("Job not found"));

	    if (!job.getCompany().getId().equals(employer.getCompany().getId())) {
	        throw new RuntimeException("Unauthorized");
	    }

	    job.setStatus(Job.JobStatus.valueOf(status.toUpperCase()));
	    return mapToResponse(job);
	}

	@Override
	public List<JobResponse> getAllActiveJobs() {
		
		 
		return jobRepository.findByStatus(Job.JobStatus.ACTIVE)
		            .stream()
		            .map(this::mapToResponse)
		            .toList();
		
	}

	@Override
	public JobResponse getJobById(Long jobId) {
		
		Job job  = jobRepository.findById(jobId).orElseThrow(()->new RuntimeException("No Job Found"));
		return mapToResponse(job);
		
	}

	@Override
	public List<JobResponse> getAllJobsByCompany() {
		Employer employer = getLoggedInEmployer();
		
		return jobRepository.findByCompany(employer.getCompany())
	            .stream()
	            .map(this::mapToResponse)
	            .toList();
	}
	
	
	//--------------------------------------------------------//
	private Employer getLoggedInEmployer() {
        return employerRepository.findByUser(utils.loggedInUser())
                .orElseThrow(() -> new RuntimeException("Employer not found"));
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
