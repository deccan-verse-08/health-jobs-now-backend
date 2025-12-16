package com.HealthJobsNow.Job.Application.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployerRequest {

	  @NotBlank(message = "Designation is required")
	    private String designation;
}
