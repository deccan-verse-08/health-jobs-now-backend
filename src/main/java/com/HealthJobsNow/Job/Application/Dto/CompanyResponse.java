package com.HealthJobsNow.Job.Application.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyResponse {
	private Long id;
    private String name;
    private String address;
    private String website;
    private String companyBannerUrl;

}
