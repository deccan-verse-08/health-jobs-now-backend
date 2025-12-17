package com.HealthJobsNow.Job.Application.Service;

import java.util.List;

import com.HealthJobsNow.Job.Application.Dto.CompanyRequest;
import com.HealthJobsNow.Job.Application.Dto.CompanyResponse;

public interface CompanyService {
	CompanyResponse createCompany(CompanyRequest request);

    CompanyResponse updateCompany(Long companyId, CompanyRequest request);

    List<CompanyResponse> getMyCompanies();

    CompanyResponse getCompanyById(Long companyId);

}
