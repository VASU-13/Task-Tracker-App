package com.vs.TaskTracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vs.TaskTracker.exception.UserProfileNotExists;
import com.vs.TaskTracker.model.Task;

import com.vs.TaskTracker.model.UserProfile;

import com.vs.TaskTracker.repository.TaskRepository;
import com.vs.TaskTracker.repository.UserProfileRepository;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	UserProfileRepository userProfileRepo;
	
	@Autowired
	TaskRepository taskRepo;
	

	
	
	@Override
	public Task addTask(Task task,String emailId) throws UserProfileNotExists {
		
		UserProfile userProfile = userProfileRepo.findByEmailId(emailId);
		
		
		
		if(userProfile!=null) {
			
			List<Task> list = getUserTaskByStatus(userProfile.getEmailId(),task.getTaskStatus());
			task.setPriority(list.size());
			task.setUserProfile(userProfile);
			task.setTimestamp();
			return taskRepo.save(task);
		}
		else {
			
			throw new UserProfileNotExists("User not exists");
		}	
	}

	

	
	@Override
	public void updateTask(Task task , String emailId) {
		
		List<Task> newlist = new ArrayList<>();
		
		Optional<Task> taskToUpdated = taskRepo.findById(task.getTaskId());
		
		
		
		if(!task.getTaskStatus().equals(taskToUpdated.get().getTaskStatus())) {
			
			List<Task> newTaskStatusList =   getUserTaskByStatus(emailId,task.getTaskStatus());
			List<Task> oldTaskStatusList =   getUserTaskByStatus(emailId,taskToUpdated.get().getTaskStatus());
			
			// For old status
			int oldPriority = taskToUpdated.get().getPriority();
			for(int i=oldPriority+1;i<oldTaskStatusList.size();i++) {
				
				Task temp = oldTaskStatusList.get(i);
				temp.setPriority(temp.getPriority()-1);
				
				newlist.add(temp);
				
				
			}
			//For new status
			int newPriority = task.getPriority();
			
			for(int i=newPriority;i<newTaskStatusList.size();i++) {
					
				
				
					Task temp = newTaskStatusList.get(i);
					temp.setPriority(temp.getPriority()+1);
					newlist.add(temp);
			}
			taskToUpdated.get().setPriority(newPriority);
			taskToUpdated.get().setTaskStatus(task.getTaskStatus());
			
			newlist.add(taskToUpdated.get());
			
			
			
		}
		
		else {
		
		List<Task> list = getUserTaskByStatus(emailId,taskToUpdated.get().getTaskStatus());		
		
		int newPriority = task.getPriority();
		int oldPriority = taskToUpdated.get().getPriority();
		
		
		for(int i=0;i<list.size();i++) {
			
			if(oldPriority < newPriority) {
				
				if(list.get(i).getPriority()>=oldPriority && list.get(i).getPriority()<=newPriority) {
					
					Task temp = new Task();
					if(oldPriority==list.get(i).getPriority()) {
						
						temp = list.get(i);
						temp.setPriority(newPriority);
						
					}
					else {
						
						temp = list.get(i);
						temp.setPriority(temp.getPriority()-1);
					}
					newlist.add(temp);
					
				}
			}
			else {
				
				if(list.get(i).getPriority()>=newPriority && list.get(i).getPriority()<=oldPriority) {
					
					Task temp = new Task();
					if(oldPriority==list.get(i).getPriority()) {
						
						temp = list.get(i);
						temp.setPriority(newPriority);
						
					}
					else {
						
						temp = list.get(i);
						temp.setPriority(temp.getPriority()+1);
					}
					newlist.add(temp);
					
					
				}
			}
			
			
			
		}
		}
		

		
		for(int i=0;i<newlist.size();i++) {
			
			taskRepo.updateTask(newlist.get(i).getTaskStatus(), newlist.get(i).getPriority(), newlist.get(i).getTaskId());
		}
	    
	}
	
	
	public List<Task> getUserTaskByStatus(String emailId,String status){
		
		UserProfile user = userProfileRepo.findByEmailId(emailId);
		return taskRepo.findAllByTaskStatusAndUserIdOrderByPriority(status,user);
	}
}
