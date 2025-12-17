package com.HealthJobsNow.Job.Application.Service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.HealthJobsNow.Job.Application.Dto.CompanyRequest;
import com.HealthJobsNow.Job.Application.Dto.CompanyResponse;
import com.HealthJobsNow.Job.Application.Model.Company;
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
public class CompanyServiceImpl implements CompanyService{
	private final CompanyRepository companyRepository;
    private final EmployerRepository employerRepository;
    private final AuthUtil authUtil;

    @Override
    public CompanyResponse createCompany(CompanyRequest request) {

        Employer employer = getLoggedInEmployer();

        if (companyRepository.existsByName(request.getName())) {
            throw new RuntimeException("Company name already exists");
        }

        Company company = new Company();
        company.setName(request.getName());
        company.setAddress(request.getAddress());
        company.setWebsite(request.getWebsite());
        company.setCompanyBannerUrl(request.getCompanyBannerUrl());
        company.setCreatedBy(employer);

        companyRepository.save(company);

        return mapToResponse(company);
    }

    @Override
    public CompanyResponse updateCompany(Long companyId, CompanyRequest request) {

        Employer employer = getLoggedInEmployer();

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        if (!company.getCreatedBy().getId().equals(employer.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        company.setAddress(request.getAddress());
        company.setWebsite(request.getWebsite());
        company.setCompanyBannerUrl(request.getCompanyBannerUrl());

        return mapToResponse(company);
    }

    @Override
    public List<CompanyResponse> getMyCompanies() {

        Employer employer = getLoggedInEmployer();

        return companyRepository.findByCreatedBy(employer)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CompanyResponse getCompanyById(Long companyId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        return mapToResponse(company);
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
                .build();
    }

}
