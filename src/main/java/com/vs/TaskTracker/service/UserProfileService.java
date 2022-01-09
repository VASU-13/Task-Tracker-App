package com.vs.TaskTracker.service;

import java.util.List;

import com.vs.TaskTracker.exception.InvalidUserProfile;
import com.vs.TaskTracker.exception.UserProfileAlreadyExists;
import com.vs.TaskTracker.exception.UserProfileNotExists;
import com.vs.TaskTracker.model.UserProfile;

public interface UserProfileService {
	
	
	public UserProfile addUserProfile(UserProfile user) throws UserProfileAlreadyExists, InvalidUserProfile;

	public void updateUserProfile(UserProfile user, String emailId) throws UserProfileNotExists;

	public void deleteUserProfile(String emailId) throws UserProfileNotExists;

	public UserProfile getUserProfile(String emailId) throws UserProfileNotExists;

	public List<UserProfile> getAllUserProfiles();
	
	public UserProfile findByEmailId(String emailId) throws UserProfileNotExists;

}
