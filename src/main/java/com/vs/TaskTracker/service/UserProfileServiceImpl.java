package com.vs.TaskTracker.service;

import java.util.Collections;
import java.util.List;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vs.TaskTracker.exception.InvalidUserProfile;
import com.vs.TaskTracker.exception.UserProfileAlreadyExists;
import com.vs.TaskTracker.exception.UserProfileNotExists;
import com.vs.TaskTracker.model.Task;
import com.vs.TaskTracker.model.UserProfile;
import com.vs.TaskTracker.repository.UserProfileRepository;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	UserProfileRepository userProfileRepo;
	
	private List<Task> sortTask(List<Task> tasks) {
		
		Collections.sort(tasks);
		return tasks;
		
	}
	
	private boolean validateUserProfile(UserProfile userProfile) {
		
		if(StringUtils.isEmpty(userProfile.getUsername()) ||  StringUtils.isEmpty(userProfile.getPassword()) || StringUtils.isEmpty(userProfile.getEmailId()))
		{
			return false;
		}
		if(StringUtils.isBlank(userProfile.getUsername()) ||  StringUtils.isBlank(userProfile.getPassword()) || StringUtils.isBlank(userProfile.getEmailId()))
		{
			return false;
		}
		return true;
	}

	@Override
	public UserProfile addUserProfile(UserProfile userProfile) throws UserProfileAlreadyExists, InvalidUserProfile {
		
		
		if(validateUserProfile(userProfile) ) {
			
			UserProfile checkUser = userProfileRepo.findByEmailId(userProfile.getEmailId());
			if(checkUser==null) {
				
				userProfile.setTimestamp();
				return userProfileRepo.save(userProfile);
			}
			else {
				
				throw new UserProfileAlreadyExists("User with emailId " + userProfile.getEmailId() + " already exists");
			}
		}
		else {
			
			throw new InvalidUserProfile("Invalid User Details");
		}
		
		
		
	}

	@Override
	public void updateUserProfile(UserProfile userProfile, String emailId) throws UserProfileNotExists {
		
		UserProfile checkUser = userProfileRepo.findByEmailId(emailId);
		if(checkUser!=null) {
			
			checkUser.setPassword(userProfile.getPassword());
			checkUser.setTimestamp();
			checkUser.setUsername(userProfile.getUsername());
			
			userProfileRepo.saveAndFlush(checkUser);
			
		}
		else {
			
			throw new UserProfileNotExists("User with emailId " + emailId + "not exists");
		}
	}

	@Override
	public void deleteUserProfile(String emailId) throws UserProfileNotExists {
		
		UserProfile checkUser = userProfileRepo.findByEmailId(emailId);
		if(checkUser!=null) {
			userProfileRepo.deleteById(checkUser.getUserId());
		}
		else {
			throw new UserProfileNotExists("User with emailId " + emailId + "not exists");
		}
		
		
	}

	@Override
	public UserProfile getUserProfile(String emailId) throws UserProfileNotExists {
		
		UserProfile checkUser = userProfileRepo.findByEmailId(emailId);
		if(checkUser!=null) {
			
			checkUser.setTasks(sortTask(checkUser.getTasks()));
			return checkUser;
		}
		else {
			throw new UserProfileNotExists("User with emailId " + emailId + "not exists");
		}
	}

	@Override
	public List<UserProfile> getAllUserProfiles() {
		
		List<UserProfile> userProfiles = userProfileRepo.findAll();
		
		return userProfiles;
	}

	@Override
	public UserProfile findByEmailId(String emailId) throws UserProfileNotExists {
		
		UserProfile res = userProfileRepo.findByEmailId(emailId);
		if(res!=null) {
			
			return res;
		}
		throw new UserProfileNotExists("User with emailId " + emailId + "not exists");
		
	}
	
	
	
	
	

	

}
