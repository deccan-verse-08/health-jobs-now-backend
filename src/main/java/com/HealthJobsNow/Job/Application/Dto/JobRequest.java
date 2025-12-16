package com.HealthJobsNow.Job.Application.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class JobRequest {
	@NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String location;

    @NotNull
    private List<String> skillsRequired;

    @NotNull
    @Min(0)
    private Integer minExperienceMonths;

    @NotNull
    private String jobType;
}
