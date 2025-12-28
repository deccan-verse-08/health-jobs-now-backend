package com.HealthJobsNow.Job.Application.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HealthJobsNow.Job.Application.Dto.CompanyRequest;
import com.HealthJobsNow.Job.Application.Dto.CompanyResponse;
import com.HealthJobsNow.Job.Application.Service.CompanyService;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employer/company") 
public class CompanyController {
    
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> create(@Valid @RequestBody CompanyRequest request) {
        // Return 201 Created for new resources
        return new ResponseEntity<>(companyService.createCompany(request), HttpStatus.CREATED);
    }

    @PutMapping
    // Removed {id} because the employer can only update THEIR own company
    public ResponseEntity<CompanyResponse> update(@Valid @RequestBody CompanyRequest request) {
        return ResponseEntity.ok(companyService.updateCompany(request));
    }

    @GetMapping("/my-company")
    public ResponseEntity<CompanyResponse> getMyCompany() {
        return ResponseEntity.ok(companyService.getMyCompany());
    }
}