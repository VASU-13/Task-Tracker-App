package com.vs.TaskTracker.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(
		uniqueConstraints = @UniqueConstraint(
				
					columnNames = { "emailId" }
				)
)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class UserProfile  {
	
	@Id
	@SequenceGenerator(
			name = "user_profile_sequence",
			sequenceName = "user_profile_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.AUTO,
			generator = "user_profile_sequence"
	)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long userId;
	private String username;
	private String emailId;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@JsonIgnore
	private LocalDateTime timestamp;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "userId")
	
    private List<Task> tasks;
	
	public UserProfile() {
		
	}
	
	
	
	public UserProfile(Long userId, String username, String emailId, String password, LocalDateTime timestamp,
			List<Task> tasks) {
		super();
		this.userId = userId;
		this.username = username;
		this.emailId = emailId;
		this.password = password;
		this.timestamp = timestamp;
		this.tasks = tasks;
	}





	public Long getUserId() {
		return userId;
	}





	public String getUsername() {
		return username;
	}





	public void setUsername(String username) {
		this.username = username;
	}





	public String getEmailId() {
		return emailId;
	}





	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}





	public String getPassword() {
		return password;
	}





	public void setPassword(String password) {
		this.password = password;
	}





	public LocalDateTime getTimestamp() {
		return timestamp;
	}





	public void setTimestamp() {
		
		LocalDateTime timestamp = LocalDateTime.now();
		this.timestamp = timestamp;
	}



	

	
	

	@JsonManagedReference
	public List<Task> getTasks() {
		return tasks;
	}





	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}



	@Override
	public String toString() {
		return "UserProfile [userId=" + userId + ", username=" + username + ", emailId=" + emailId + ", password="
				+ password + ", timestamp=" + timestamp + ", tasks=" + tasks + "]";
	}



	

}
