package com.HealthJobsNow.Job.Application.Dto;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobSeekerResponse {
	
	    private Long id;

	    private String resumeUrl;

	    private String profilePicUrl;

	    private List<String> skills;

	    private Integer experienceMonths;

	    private Double experienceYears;

}
