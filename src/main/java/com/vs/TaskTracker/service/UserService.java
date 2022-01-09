package com.vs.TaskTracker.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vs.TaskTracker.exception.UserProfileNotExists;
import com.vs.TaskTracker.model.UserProfile;
import com.vs.TaskTracker.repository.UserProfileRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserProfileRepository userProfileRepo;
	
	@Override
	public UserDetails loadUserByUsername(String emailId)  {
		
		//get user from db
		UserProfile userProfile = userProfileRepo.findByEmailId(emailId);
		
		
		return new User(userProfile.getEmailId(),userProfile.getPassword(),new ArrayList<>());
		
	}

}
