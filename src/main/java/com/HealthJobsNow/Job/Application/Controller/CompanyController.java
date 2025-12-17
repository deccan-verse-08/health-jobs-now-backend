package com.HealthJobsNow.Job.Application.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HealthJobsNow.Job.Application.Dto.CompanyRequest;
import com.HealthJobsNow.Job.Application.Dto.CompanyResponse;
import com.HealthJobsNow.Job.Application.Service.CompanyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employer/company")
public class CompanyController {
	private final CompanyService companyService;

    @PostMapping
    public CompanyResponse create(@RequestBody CompanyRequest request) {
        return companyService.createCompany(request);
    }

    @PutMapping("/{id}")
    public CompanyResponse update(
            @PathVariable Long id,
            @RequestBody CompanyRequest request) {
        return companyService.updateCompany(id, request);
    }

    @GetMapping
    public List<CompanyResponse> myCompanies() {
        return companyService.getMyCompanies();
    }

    @GetMapping("/{id}")
    public CompanyResponse getById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }
}
