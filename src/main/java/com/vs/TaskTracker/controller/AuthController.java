package com.vs.TaskTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vs.TaskTracker.model.JwtRequest;
import com.vs.TaskTracker.model.JwtResponse;
import com.vs.TaskTracker.service.UserService;
import com.vs.TaskTracker.utility.JwtUtility;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
	
	@Autowired
	private AuthenticationManager  authenticationManager;
	
	@Autowired
	private JwtUtility jwtUtility;
	
	@Autowired
	private UserService userService;
	

	@PostMapping("/auth")
	public ResponseEntity<?>  authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
		
		ResponseEntity<?> res = null;
		try {
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
			final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
			final String token =  jwtUtility.generateToken(userDetails); 
			res = new ResponseEntity<JwtResponse>( new JwtResponse(token) ,HttpStatus.OK);
			
		} catch(Exception e) {	
			
			res = new ResponseEntity<String>(" Incorrect Email / Password " ,HttpStatus.NOT_FOUND);
			
		}
		return res;
		
		
		
	}

}
