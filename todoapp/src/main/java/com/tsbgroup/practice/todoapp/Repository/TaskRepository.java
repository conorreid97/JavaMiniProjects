package com.tsbgroup.practice.todoapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tsbgroup.practice.todoapp.Models.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

}

