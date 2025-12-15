package com.HealthJobsNow.Job.Application.Dto;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class JobSeekerRequest {

	private String resumeUrl;
    
    private String profilePicUrl;
    
    private List<String> skills;
    
    @NotNull(message = "Experience (in months) is required")
    @Min(value = 0, message = "Experience must be >= 0 months")
    @Max(value = 600, message = "Experience must be <= 600 months")
    private Integer experienceMonths;
    

}
