package com.vs.TaskTracker.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.vs.TaskTracker.model.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
	
	
	
	public UserProfile findByEmailId(String emailId);
	

	
	
	
	
	

}