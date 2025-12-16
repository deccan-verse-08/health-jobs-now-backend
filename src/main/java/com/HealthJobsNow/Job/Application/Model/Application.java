package com.HealthJobsNow.Job.Application.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(
	name = "applications",
	uniqueConstraints = {
	@UniqueConstraint(columnNames = {"job_id", "jobseeker_id"})
	})
public class Application {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "job_id",nullable = false)
	private Job job;
	
	@ManyToOne
	@JoinColumn(name = "jobseeker_id",nullable = false)
	private JobSeeker jobSeeker;
	
	private String resumeUrl;
	
	@Builder.Default
	@Enumerated(EnumType.STRING)
	private Status status = Status.PENDING;

	public enum Status{
		PENDING,SHORTLISTED,REJECTED,HIRED
	}
	
	@Builder.Default
	private LocalDateTime appliedAt = LocalDateTime.now();
	

}
