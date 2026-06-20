package com.pm.corecrm.service;

import com.pm.corecrm.domain.dto.task.AssignTaskRequest;
import com.pm.corecrm.domain.dto.task.CreateTaskRequest;
import com.pm.corecrm.domain.dto.task.TaskDto;
import com.pm.corecrm.domain.dto.task.UpdateTaskRequest;
import com.pm.corecrm.domain.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    TaskDto createTask(CreateTaskRequest request);

    TaskDto getTaskById(Long id);

    List<TaskDto> getAllTasks();

    Page<TaskDto> getAllTasks(Task.TaskStatus status, Long assigneeId, Long buildingId, Pageable pageable);

    List<TaskDto> getTasksByAssignee(Long userId);

    List<TaskDto> getTasksByStatus(Task.TaskStatus status);

    List<TaskDto> getTasksByBuilding(Long buildingId);

    TaskDto updateTask(Long id, UpdateTaskRequest request);

    TaskDto assignTask(Long id, AssignTaskRequest request);

    TaskDto updateStatus(Long id, Task.TaskStatus status);

    void deleteTask(Long id);
}