package com.HealthJobsNow.Job.Application.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
	
	  @NotBlank(message = "Name is required")
	    @Size(min = 2, max = 50)
	    private String name;

	    @NotBlank(message = "Email is required")
	    @Email(message = "Invalid email format")
	    private String email;

	    @NotBlank(message = "Password is required")
	    @Size(min = 8, message = "Password must be at least 8 characters")
	    private String password;

	    @NotBlank(message = "Phone number is required")
	    @Pattern(
	        regexp = "^[6-9]\\d{9}$",
	        message = "Invalid Indian mobile number"
	    )
	    private String phone;

}
