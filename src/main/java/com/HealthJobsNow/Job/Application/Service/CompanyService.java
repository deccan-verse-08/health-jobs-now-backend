package com.HealthJobsNow.Job.Application.Service;

import java.util.List;

import com.HealthJobsNow.Job.Application.Dto.CompanyRequest;
import com.HealthJobsNow.Job.Application.Dto.CompanyResponse;
import com.HealthJobsNow.Job.Application.Model.Company;

public interface CompanyService {
	CompanyResponse createCompany(CompanyRequest request);

    CompanyResponse updateCompany(CompanyRequest request);

    
    
    CompanyResponse validateCompany(Long id,String status);

	CompanyResponse getMyCompany();
	
	CompanyResponse getCompanyById(Long id);
	
	List<Company> getAllCompanies();

	

	

}
