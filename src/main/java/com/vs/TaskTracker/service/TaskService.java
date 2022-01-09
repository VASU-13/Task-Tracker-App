package com.vs.TaskTracker.service;


import java.util.List;

import com.vs.TaskTracker.exception.UserProfileNotExists;
import com.vs.TaskTracker.model.Task;




public interface TaskService {
	
	public Task addTask(Task task,String emailId) throws UserProfileNotExists;
	public void updateTask(Task task,String emailId);
	public List<Task> getUserTaskByStatus(String emailId,String status);
	
	
}
