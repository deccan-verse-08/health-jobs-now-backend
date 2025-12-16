package com.HealthJobsNow.Job.Application.Dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationResponse {
	 private Long applicationId;

	    private Long jobId;
	    private String jobTitle;

	    private Long jobSeekerId;
	    private String jobSeekerName;

	    private String resumeUrl;
	    private Integer experienceMonths;

	    private String status;
	    private LocalDateTime appliedAt;
}
