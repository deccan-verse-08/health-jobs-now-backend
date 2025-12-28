package com.HealthJobsNow.Job.Application.Service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.HealthJobsNow.Job.Application.Dto.CompanyRequest;
import com.HealthJobsNow.Job.Application.Dto.CompanyResponse;
import com.HealthJobsNow.Job.Application.Model.Company;
import com.HealthJobsNow.Job.Application.Model.CompanyStatus;
import com.HealthJobsNow.Job.Application.Model.Employer;
import com.HealthJobsNow.Job.Application.Repository.CompanyRepository;
import com.HealthJobsNow.Job.Application.Repository.EmployerRepository;
import com.HealthJobsNow.Job.Application.Service.CompanyService;
import com.HealthJobsNow.Job.Application.Utils.AuthUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final EmployerRepository employerRepository;
    private final AuthUtil authUtil;

    @Override
    public CompanyResponse createCompany(CompanyRequest request) {

        Employer employer = getLoggedInEmployer();

        if (employer.getCompany() != null) {
            throw new RuntimeException("Company already exists for this employer");
        }

        if (companyRepository.existsByName(request.getName())) {
            throw new RuntimeException("Company name already exists");
        }

        Company company = new Company();
        company.setName(request.getName());
        company.setAddress(request.getAddress());
        company.setWebsite(request.getWebsite());
        company.setCompanyBannerUrl(request.getCompanyBannerUrl());
        company.setStatus(CompanyStatus.PENDING);
        company.setCreatedBy(employer);

        companyRepository.save(company);

        // 🔗 VERY IMPORTANT
        employer.setCompany(company);
        employerRepository.save(employer);

        return mapToResponse(company);
    }

    @Override
    public CompanyResponse updateCompany(CompanyRequest request) {

        Employer employer = getLoggedInEmployer();

        Company company = employer.getCompany();
        if (company == null) {
            throw new RuntimeException("Company not created yet");
        }

        company.setAddress(request.getAddress());
        company.setWebsite(request.getWebsite());
        company.setCompanyBannerUrl(request.getCompanyBannerUrl());
        companyRepository.save(company);

        return mapToResponse(company);
    }

    @Override
    public CompanyResponse getMyCompany() {

        Employer employer = getLoggedInEmployer();

        if (employer.getCompany() == null) {
            throw new RuntimeException("Company not created yet");
        }

        return mapToResponse(employer.getCompany());
    }

    private Employer getLoggedInEmployer() {
        return employerRepository.findByUser(authUtil.loggedInUser())
                .orElseThrow(() -> new RuntimeException("Employer not found"));
    }

    private CompanyResponse mapToResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .address(company.getAddress())
                .website(company.getWebsite())
                .companyBannerUrl(company.getCompanyBannerUrl())
                .status(company.getStatus())
                .build();
    }



	@Override
	public List<Company> getAllCompanies() {
		// TODO Auto-generated method stub
		
		return companyRepository.findAll();
	}

	@Override
	public CompanyResponse getCompanyById(Long companyId) {
	    Company company = companyRepository.findById(companyId).orElseThrow(()->new RuntimeException("No Company found"));
	    
		return mapToResponse(company);
	}

	@Override
	public CompanyResponse validateCompany(Long id ,String status) {
        Company company = companyRepository.findById(id).orElseThrow(()->new RuntimeException("Company not found"));
        company.setStatus(CompanyStatus.valueOf(status));
		return mapToResponse(company);
	}

	
}
