package com.HealthJobsNow.Job.Application.Service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.HealthJobsNow.Job.Application.Dto.ApplicationResponse;
import com.HealthJobsNow.Job.Application.Dto.EmployerRequest;
import com.HealthJobsNow.Job.Application.Dto.EmployerResponse;
import com.HealthJobsNow.Job.Application.Model.Application;
import com.HealthJobsNow.Job.Application.Model.Employer;
import com.HealthJobsNow.Job.Application.Model.EmployerStatus;
import com.HealthJobsNow.Job.Application.Model.Job;
import com.HealthJobsNow.Job.Application.Model.User;
import com.HealthJobsNow.Job.Application.Model.UserRole;
import com.HealthJobsNow.Job.Application.Repository.ApplicationRepository;
import com.HealthJobsNow.Job.Application.Repository.EmployerRepository;
import com.HealthJobsNow.Job.Application.Repository.JobRepository;
import com.HealthJobsNow.Job.Application.Service.EmployerService;
import com.HealthJobsNow.Job.Application.Utils.AuthUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepository employerRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final AuthUtil authUtil;

    // ---------------- PROFILE ----------------

    @Override
    public EmployerResponse getProfile() {
        Employer employer = getLoggedInEmployer();
        return mapEmployerResponse(employer);
    }

    @Override
    public EmployerResponse createProfile(EmployerRequest request) {

        User user = authUtil.loggedInUser();

        if (user.getRole() != UserRole.EMPLOYER) {
            throw new RuntimeException("Access denied");
        }

        employerRepository.findByUser(user).ifPresent(e -> {
            throw new RuntimeException("Employer profile already exists");
        });

        Employer employer = new Employer();
        employer.setUser(user);
        employer.setDesignation(request.getDesignation());
        employer.setStatus(EmployerStatus.PENDING);
        

        employerRepository.save(employer);

        return mapEmployerResponse(employer);
    }

    @Override
    public EmployerResponse updateProfile(EmployerRequest request) {
        Employer employer = getLoggedInEmployer();
        employer.setDesignation(request.getDesignation());
        employerRepository.save(employer);
        return mapEmployerResponse(employer);
    }

    // ---------------- APPLICATIONS ----------------

    @Override
    public List<ApplicationResponse> getApplicationsByJob(Long jobId) {

        Employer employer = getLoggedInEmployer();
        ensureEmployerHasCompany(employer);

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getCompany().getId().equals(employer.getCompany().getId())) {
            throw new RuntimeException("Unauthorized to view applications");
        }

        return applicationRepository.findByJob(job)
                .stream()
                .map(this::mapApplicationResponse)
                .toList();
    }

    @Override
    public ApplicationResponse changeApplicationStatus(
            Long applicationId,
            Application.Status status) {

        Employer employer = getLoggedInEmployer();
        ensureEmployerHasCompany(employer);

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!application.getJob().getCompany().getId()
                .equals(employer.getCompany().getId())) {
            throw new RuntimeException("Unauthorized");
        }

        application.setStatus(status);
        applicationRepository.save(application);

        return mapApplicationResponse(application);
    }

    // ---------------- STATS ----------------

    @Override
    public Map<String, Long> getApplicationStats() {

        Employer employer = getLoggedInEmployer();
        ensureEmployerHasCompany(employer);

        List<Application> applications =
                applicationRepository.findByJob_Company(employer.getCompany());

        Map<String, Long> stats = new HashMap<>();
        stats.put("total", (long) applications.size());
        stats.put("pending", applications.stream()
                .filter(a -> a.getStatus() == Application.Status.PENDING).count());
        stats.put("shortlisted", applications.stream()
                .filter(a -> a.getStatus() == Application.Status.SHORTLISTED).count());
        stats.put("rejected", applications.stream()
                .filter(a -> a.getStatus() == Application.Status.REJECTED).count());
        stats.put("hired", applications.stream()
                .filter(a -> a.getStatus() == Application.Status.HIRED).count());

        return stats;
    }

    // ---------------- HELPERS ----------------

    private Employer getLoggedInEmployer() {
        return employerRepository.findByUser(authUtil.loggedInUser())
                .orElseThrow(() -> new RuntimeException("Employer not found"));
    }

    private void ensureEmployerHasCompany(Employer employer) {
        if (employer.getCompany() == null) {
            throw new RuntimeException("Employer is not associated with any company");
        }
    }

    private EmployerResponse mapEmployerResponse(Employer employer) {

        Long companyId = null;
        String companyName = null;

        if (employer.getCompany() != null) {
            companyId = employer.getCompany().getId();
            companyName = employer.getCompany().getName();
        }

        return EmployerResponse.builder()
                .id(employer.getId())
                .designation(employer.getDesignation())
                .status(employer.getStatus().name())
                .companyId(companyId)
                .companyName(companyName)
                .build();
    }

    private ApplicationResponse mapApplicationResponse(Application app) {
        return ApplicationResponse.builder()
                .applicationId(app.getId())
                .jobId(app.getJob().getId())
                .jobTitle(app.getJob().getTitle())
                .jobSeekerId(app.getJobSeeker().getId())
                .jobSeekerName(app.getJobSeeker().getUser().getName())
                .resumeUrl(app.getResumeUrl())
                .experienceMonths(app.getJobSeeker().getExperienceMonths())
                .status(app.getStatus().name())
                .appliedAt(app.getAppliedAt())
                .build();
    }

	@Override
	public EmployerResponse updateStatus(Long id,String status) {
		Employer emp = employerRepository.findById(id).orElseThrow(()->new RuntimeException(""));
		emp.setStatus(EmployerStatus.valueOf(status));
		employerRepository.save(emp);
		return mapEmployerResponse(emp);
	}

	@Override
	public List<Employer> getAllEmployer() {
		
		return employerRepository.findAll();
	}
}
