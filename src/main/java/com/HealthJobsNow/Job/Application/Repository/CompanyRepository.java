package com.HealthJobsNow.Job.Application.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HealthJobsNow.Job.Application.Model.Company;
import com.HealthJobsNow.Job.Application.Model.Employer;

public interface CompanyRepository extends JpaRepository<Company, Long>{

	List<Company> findByCreatedBy(Employer employer);

    boolean existsByName(String name);
}
