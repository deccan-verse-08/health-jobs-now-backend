package com.HealthJobsNow.Job.Application.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "jobs")
public class Job {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "company_id",nullable = false)
	private Company company;
	
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	private String location;
	
	private LocalDateTime createAt = LocalDateTime.now();
	

}
