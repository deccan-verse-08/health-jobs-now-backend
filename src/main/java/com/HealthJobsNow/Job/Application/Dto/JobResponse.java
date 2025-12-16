package com.HealthJobsNow.Job.Application.Dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JobResponse {
	
	private Long id;

    private String title;

    private String description;

    private String location;

    private List<String> skillsRequired;

    private Integer minExperienceMonths;

    private String jobType;

    private String status;

    private String companyName;

    private LocalDateTime createdAt;
}
