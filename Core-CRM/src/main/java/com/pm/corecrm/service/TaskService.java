package com.pm.corecrm.service;

import com.pm.corecrm.domain.dto.task.AssignTaskRequest;
import com.pm.corecrm.domain.dto.task.CreateTaskRequest;
import com.pm.corecrm.domain.dto.task.TaskDto;
import com.pm.corecrm.domain.dto.task.UpdateTaskRequest;
import com.pm.corecrm.domain.entity.Task;

import java.util.List;

public interface TaskService {

    TaskDto createTask(CreateTaskRequest request);

    TaskDto getTaskById(Long id);

    List<TaskDto> getAllTasks();

    List<TaskDto> getTasksByAssignee(Long userId);

    List<TaskDto> getTasksByStatus(Task.TaskStatus status);

    List<TaskDto> getTasksByBuilding(Long buildingId);

    TaskDto updateTask(Long id, UpdateTaskRequest request);

    TaskDto assignTask(Long id, AssignTaskRequest request);

    TaskDto updateStatus(Long id, Task.TaskStatus status);

    void deleteTask(Long id);
}