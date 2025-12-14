package com.HealthJobsNow.Job.Application.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String phone;
	

}
