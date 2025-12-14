package com.HealthJobsNow.Job.Application.Service;

import com.HealthJobsNow.Job.Application.Model.User;

public interface UserService {
	User createUser(User user);
	
	User updateUser(User user);

}
