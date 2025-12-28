package com.HealthJobsNow.Job.Application.Model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String location;

    @ElementCollection
    @CollectionTable(
        name = "job_skills",
        joinColumns = @JoinColumn(name = "job_id")
    )
    @Column(name = "skill")
    private List<String> skillsRequired;

    @Column(nullable = false)
    private Integer minExperienceMonths;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType jobType;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status = JobStatus.ACTIVE;

    @Builder.Default
    @Column(nullable = false , updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum JobStatus {
        ACTIVE,
        CLOSED
    }

    public enum JobType {
        FULL_TIME,
        PART_TIME,
        CONTRACT,
        INTERNSHIP
    }
}