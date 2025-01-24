package com.tsbgroup.practice.todoapp.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tsbgroup.practice.todoapp.Models.Task;
import com.tsbgroup.practice.todoapp.Repository.TaskRepository;

@Service
public class TaskService {

	private final TaskRepository taskRepository;
	
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	public List<Task> getAllTasks() {
		// TODO get access to the task repository and return list of all tasks
		return taskRepository.findAll();
	}

	public void createTask(String title) {
		// TODO Auto-generated method stub
		Task task = new Task();
		task.setTitle(title);
		task.setCompleted(false);
		taskRepository.save(task);
	}

	public void deleteTask(Long id) {
		// TODO Auto-generated method stub
		taskRepository.deleteById(id);
	}

	public void toggleTask(Long id) {
		// TODO Auto-generated method stub
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid task id"));
		task.setCompleted(!task.isCompleted());
		
		taskRepository.save(task);
	}

}
