package com.HealthJobsNow.Job.Application.Dto;

import com.HealthJobsNow.Job.Application.Model.Application;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ApplicationStatusRequest {
    @NotNull
    private Application.Status status;
}
