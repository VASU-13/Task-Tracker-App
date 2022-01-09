package com.vs.TaskTracker.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
public class Task implements Comparable<Task>{
	
	@Id
	@SequenceGenerator(
			name = "task_sequence",
			sequenceName = "task_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "teacher_sequence"
	)
	private Long taskId;
	private String taskName;
	private String taskDescription;
	private String taskStatus;
	private int priority;
	private LocalDateTime timestamp;
	@ManyToOne(
			fetch = FetchType.EAGER
	)
	@JoinColumn(
			name = "userId",
			nullable=false
	)
	private UserProfile userId;
	
	
	
	
	public Task() {
		super();
	}




	public Task(Long taskId, String taskName, String taskDescription, String taskStatus, int priority,
			LocalDateTime timestamp, UserProfile userId) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskDescription = taskDescription;
		this.taskStatus = taskStatus;
		this.priority = priority;
		this.timestamp = timestamp;
		this.userId = userId;
	}




	public Task(Long taskId, String taskStatus, int priority) {
		super();
		this.taskId = taskId;
		this.taskStatus = taskStatus;
		this.priority = priority;
	}




	public Task(int priority) {
		super();
		this.priority = priority;
	}




	public Long getTaskId() {
		return taskId;
	}




	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}




	public String getTaskName() {
		return taskName;
	}




	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}




	public String getTaskDescription() {
		return taskDescription;
	}




	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}




	public String getTaskStatus() {
		return taskStatus;
	}




	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}




	public int getPriority() {
		return priority;
	}




	public void setPriority(int priority) {
		this.priority = priority;
	}




	public LocalDateTime getTimestamp() {
		return timestamp;
	}




	public void setTimestamp() {
		
		LocalDateTime timestamp = LocalDateTime.now();
		this.timestamp = timestamp;
	}



    @JsonBackReference
	public UserProfile getUserProfile() {
		return userId;
	}




	public void setUserProfile(UserProfile userId) {
		this.userId = userId;
	}




	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", taskName=" + taskName + ", taskDescription=" + taskDescription
				+ ", taskStatus=" + taskStatus + ", priority=" + priority + ", timestamp=" + timestamp
				+ ", userProfile=" + userId + "]";
	}




	@Override
	public int compareTo(Task o) {
		
		if(this.priority!=o.getPriority()) {
			
			return this.priority - o.getPriority();
					
		}
		
		return this.taskStatus.compareTo(o.getTaskStatus());
	}
	
	
	
	
	

}
