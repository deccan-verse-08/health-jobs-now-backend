package com.HealthJobsNow.Job.Application.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployerResponse {
	 private Long id;
	    private String designation;
	    private String status;
	    private String companyName;
	    private Long companyId;

}
