package com.HealthJobsNow.Job.Application.Dto;

import lombok.Data;

@Data
public class MessageResponse {
	 private String message;

	 public MessageResponse(String message){
	        this.message = message;
	    }

}
