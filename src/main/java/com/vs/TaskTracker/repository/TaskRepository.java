package com.vs.TaskTracker.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vs.TaskTracker.model.Task;

import com.vs.TaskTracker.model.UserProfile;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
	
	public List<Task> findAllByTaskStatusAndUserIdOrderByPriority(String taskStatus,UserProfile userId);
	
	
	@Transactional
	@Modifying
	@Query(
			
			"update Task t set t.taskStatus=?1 , t.priority=?2 where t.taskId=?3"
	)
	public void updateTask(String taskStatus, int priority, long taskId);
	
	
	
	

	
}
