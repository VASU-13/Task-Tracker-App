package com.vs.TaskTracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vs.TaskTracker.exception.InvalidUserProfile;
import com.vs.TaskTracker.exception.UserProfileAlreadyExists;
import com.vs.TaskTracker.exception.UserProfileNotExists;
import com.vs.TaskTracker.model.UserProfile;
import com.vs.TaskTracker.service.UserProfileService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000/")
public class UserController {

	@Autowired
	UserProfileService userProfileService;
	
	
	/* Add UserProfile */
	
	@PostMapping("/user/add")
	public ResponseEntity<?> addUser(@RequestBody UserProfile userProfile)  {
		
		
			ResponseEntity<?> res = null;
			try {
				
				UserProfile addedUserProfile = userProfileService.addUserProfile(userProfile);
				res = new ResponseEntity<UserProfile>(addedUserProfile,HttpStatus.CREATED);
				
				
			} catch (UserProfileAlreadyExists e) {
				
				res = new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
			}
			catch (InvalidUserProfile e) {
				
				res = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
			}
			return res;
		
	}
	
	
	/* Get all User */
	
	@GetMapping("/user")
	 public ResponseEntity<?> getAllUserProfiles() {
		
		 
		 List<UserProfile> userProfiles = userProfileService.getAllUserProfiles();
		 return new ResponseEntity<List<UserProfile>>(userProfiles,HttpStatus.OK); 
		 
	 }
	
	
	
	 
	
	
	/* Get specific user with emailId */
	
	@GetMapping("/user/{emailId}")
	 public ResponseEntity<?> getUserProfileByEmailId(@PathVariable String emailId) {
		
			ResponseEntity<?> response = null;
			try {
				
				UserProfile userProfile = userProfileService.getUserProfile(emailId);
				
				if(userProfile!=null) {
					
					response = new ResponseEntity<UserProfile>(userProfile,HttpStatus.OK);
				}
				
			} 
			catch (UserProfileNotExists e) {
				
				response = new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
			}
			
			return response;
			 
			 
		 }
	
	
	
	
	/* Update userProfile */
	
	@PutMapping("/{emailId}")
	 public ResponseEntity<?> updateUserProfile(@RequestBody UserProfile userProfile, @PathVariable String emailId) throws UserProfileNotExists {
		
		 ResponseEntity<?> res = null;
		 try {
				userProfileService.updateUserProfile(userProfile, emailId);
				res = new ResponseEntity<String>("UserProfile updated successfully",HttpStatus.OK);
				
			} catch (UserProfileNotExists e) {
				
				res = new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
			}
		    return res;
		 
		 
	 }
	
	
	/* Delete userProfile */
	
	@DeleteMapping("/{emailId}")
	 public ResponseEntity<?> deleteUserProfile(@PathVariable String emailId) throws UserProfileNotExists {
		
		 ResponseEntity<?> res = null;
		 try {
				userProfileService.deleteUserProfile(emailId);
				res = new ResponseEntity<String>("UserProfile deleted successfully",HttpStatus.OK);
				
			} catch (UserProfileNotExists e) {
				
				res = new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
			}
		    return res;
		 
		 
	 }
	
	

}
