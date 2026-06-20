package com.pm.corecrm.service.impl;

import com.pm.corecrm.domain.dto.task.AssignTaskRequest;
import com.pm.corecrm.domain.dto.task.CreateTaskRequest;
import com.pm.corecrm.domain.dto.task.TaskDto;
import com.pm.corecrm.domain.dto.task.UpdateTaskRequest;
import com.pm.corecrm.domain.entity.Task;
import com.pm.corecrm.service.TaskService;

import java.util.List;

public class TaskServiceImpl implements TaskService {
    @Override
    public TaskDto createTask(CreateTaskRequest request) {
        return null;
    }

    @Override
    public TaskDto getTaskById(Long id) {
        return null;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return List.of();
    }

    @Override
    public List<TaskDto> getTasksByAssignee(Long userId) {
        return List.of();
    }

    @Override
    public List<TaskDto> getTasksByStatus(Task.TaskStatus status) {
        return List.of();
    }

    @Override
    public List<TaskDto> getTasksByBuilding(Long buildingId) {
        return List.of();
    }

    @Override
    public TaskDto updateTask(Long id, UpdateTaskRequest request) {
        return null;
    }

    @Override
    public TaskDto assignTask(Long id, AssignTaskRequest request) {
        return null;
    }

    @Override
    public TaskDto updateStatus(Long id, Task.TaskStatus status) {
        return null;
    }

    @Override
    public void deleteTask(Long id) {

    }
}
