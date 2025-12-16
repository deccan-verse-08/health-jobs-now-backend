package com.HealthJobsNow.Job.Application.Service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.HealthJobsNow.Job.Application.Dto.JobSeekerRequest;
import com.HealthJobsNow.Job.Application.Dto.JobSeekerResponse;
import com.HealthJobsNow.Job.Application.Model.Application;
import com.HealthJobsNow.Job.Application.Model.Job;
import com.HealthJobsNow.Job.Application.Model.JobSeeker;
import com.HealthJobsNow.Job.Application.Repository.ApplicationRepository;
import com.HealthJobsNow.Job.Application.Repository.JobRepository;
import com.HealthJobsNow.Job.Application.Repository.JobSeekerRepository;
import com.HealthJobsNow.Job.Application.Service.JobSeekerService;
import com.HealthJobsNow.Job.Application.Utils.AuthUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JobSeekerServiceImpl implements JobSeekerService{
	
	private final JobSeekerRepository jobSeekerRepo;
	private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final AuthUtil authUtil;
	
	
	@Override
	public JobSeekerResponse getProfile() {
		JobSeeker jobSeeker = jobSeekerRepo.findByUser(authUtil.loggedInUser()).orElseThrow(()->new RuntimeException("User profile not found"));
		
		return mapToResponse(jobSeeker);
	}

	@Override
	public JobSeekerResponse updateProfile(JobSeekerRequest request) {
		JobSeeker jobSeeker = jobSeekerRepo.findByUser(authUtil.loggedInUser()).orElse(JobSeeker.builder().user(authUtil.loggedInUser()).build());
		JobSeekerResponse response = new JobSeekerResponse();
		jobSeeker.setResumeUrl(request.getResumeUrl());
        jobSeeker.setProfilePicUrl(request.getProfilePicUrl());
        jobSeeker.setSkills(request.getSkills());
        jobSeeker.setExperienceMonths(request.getExperienceMonths());
        jobSeekerRepo.save(jobSeeker);
        
		return mapToResponse(jobSeeker);
	}

	@Override
	public List<Map<String, Object>> getApplications() {
		JobSeeker jobSeeker = jobSeekerRepo.findByUser(authUtil.loggedInUser())
								.orElseThrow(()-> new RuntimeException("JobSeeker profile not found"));
		 List<Application> applications = applicationRepository.findByJobSeeker(jobSeeker);
		 return applications.stream().map(app->{
			 Map<String, Object> map = new HashMap<String, Object>();
			 Job job = app.getJob();
			 map.put("jobTitle", job.getTitle());
			 map.put("companyName", job.getCompany().getName());
			 map.put("status", app.getStatus());
			 map.put("appliedAt", app.getAppliedAt());
			 return map;
		 }).collect(Collectors.toList());
		
	}

	@Override
	public Map<String, Object> applyJob(Long jobId) {
		JobSeeker jobSeeker = jobSeekerRepo.findByUser(authUtil.loggedInUser())
                .orElseThrow(() -> new RuntimeException("JobSeeker profile not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        if (applicationRepository.existsByJobSeekerAndJob(jobSeeker, job)) {
            throw new RuntimeException("Already applied for this job");
        }

        // Check resume exists
        if (jobSeeker.getResumeUrl() == null || jobSeeker.getResumeUrl().isEmpty()) {
            throw new RuntimeException("Upload resume before applying");
        }
        Application application = Application.builder()
                .job(job)
                .jobSeeker(jobSeeker)
                
                
                .resumeUrl(jobSeeker.getResumeUrl())
                .build();
        applicationRepository.save(application);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Applied successfully");
        response.put("jobTitle", job.getTitle());
        response.put("companyName", job.getCompany().getName());
        return response;
		
	}

	@Override
	public Map<String, Long> getApplicationCount() {
		// TODO Auto-generated method stub
		JobSeeker jobSeeker = jobSeekerRepo.findByUser(authUtil.loggedInUser())
                .orElseThrow(() -> new RuntimeException("JobSeeker profile not found"));

        List<Application> applications = applicationRepository.findByJobSeeker(jobSeeker);

        Map<String, Long> countMap = new HashMap<>();
        countMap.put("total", (long) applications.size());
        countMap.put("pending", applications.stream().filter(a -> a.getStatus() == Application.Status.PENDING).count());
        countMap.put("rejected", applications.stream().filter(a -> a.getStatus() == Application.Status.REJECTED).count());
        countMap.put("hired", applications.stream().filter(a -> a.getStatus() == Application.Status.HIRED).count());

        return countMap;
	}

	@Override
	public Integer getProfileCompleteness() {
		JobSeeker jobSeeker = jobSeekerRepo.findByUser(authUtil.loggedInUser())
                .orElseThrow(() -> new RuntimeException("JobSeeker profile not found"));

        int completeness = 0;

        if (jobSeeker.getResumeUrl() != null && !jobSeeker.getResumeUrl().isEmpty()) completeness += 30;
        if (jobSeeker.getSkills() != null && !jobSeeker.getSkills().isEmpty()) completeness += 25;
        if (jobSeeker.getExperienceMonths() != null && jobSeeker.getExperienceMonths() > 0) completeness += 25;
        if (jobSeeker.getProfilePicUrl() != null && !jobSeeker.getProfilePicUrl().isEmpty()) completeness += 20;

        return completeness;
	}
	
	
	
	private JobSeekerResponse mapToResponse(JobSeeker jobSeeker) {
        return JobSeekerResponse.builder()
                .id(jobSeeker.getId())
                .resumeUrl(jobSeeker.getResumeUrl())
                .profilePicUrl(jobSeeker.getProfilePicUrl())
                .skills(jobSeeker.getSkills())
                .experienceMonths(jobSeeker.getExperienceMonths())
                .experienceYears(jobSeeker.getExperienceMonths() / 12.0)
                .build();
    }

}
