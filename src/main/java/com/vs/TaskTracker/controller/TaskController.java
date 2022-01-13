package com.vs.TaskTracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vs.TaskTracker.exception.UserProfileNotExists;
import com.vs.TaskTracker.model.Task;

import com.vs.TaskTracker.service.TaskService;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000/")
public class TaskController {

	@Autowired
	TaskService taskService;
	
	
	/* Add UserProfile logic */
	
	@PostMapping("/task/{emailId}")
	public ResponseEntity<?> addTask(@RequestBody Task task,@PathVariable String emailId) throws UserProfileNotExists  {
		
		
		Task addTask = taskService.addTask(task,emailId);
		return new ResponseEntity<Task>(addTask,HttpStatus.CREATED);	
		
	}
	

	
	
	@PutMapping("/task/{emailId}")
	public ResponseEntity<?> updateTask(@RequestBody Task task, @PathVariable String emailId)   {
		
		taskService.updateTask(task,emailId);
		return new ResponseEntity<String>("Task updated",HttpStatus.OK);	
		
	}
	
	

	@GetMapping("/tasks/status/{emailId}/{status}")
	public ResponseEntity<?> getAllTask(@PathVariable String emailId, @PathVariable String status)   {
		
		List<Task> userTask = taskService.getUserTaskByStatus(emailId,status);
		return new ResponseEntity<List<Task>>(userTask,HttpStatus.OK);	
		
	}
	
}