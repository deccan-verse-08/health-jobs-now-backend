package com.HealthJobsNow.Job.Application.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HealthJobsNow.Job.Application.Dto.CompanyResponse;
import com.HealthJobsNow.Job.Application.Service.CompanyService;
import com.HealthJobsNow.Job.Application.Service.EmployerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
	
	private final CompanyService companyService;
	private final EmployerService employerService;
	
	// ---- company ke related apis ----- //
	
	@GetMapping("/company")
	public ResponseEntity<?> getAllCompanies(){
		return ResponseEntity.ok(companyService.getAllCompanies());
	}
	
	@GetMapping("/company/{id}")
	public ResponseEntity<?> getCompanyById(@PathVariable Long id){
		return ResponseEntity.ok(companyService.getCompanyById(id));
	}
	
	
	@PutMapping("/company/{id}/{status}")
	public ResponseEntity<?> validateCompany(@PathVariable Long id ,@PathVariable String status){
		CompanyResponse response = companyService.validateCompany(id, status);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/employer")
	public ResponseEntity<?> getAllEmployer(){
		return ResponseEntity.ok(employerService.getAllEmployer());
	}
	
	@GetMapping("/employer/{id}/{status}")
	public ResponseEntity<?> verifyEmployer(@PathVariable Long id , @PathVariable String status){
		return ResponseEntity.ok(employerService.updateStatus(id, status));
	}
	
	

}
