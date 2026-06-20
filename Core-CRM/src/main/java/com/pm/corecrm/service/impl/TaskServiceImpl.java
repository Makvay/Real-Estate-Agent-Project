package com.pm.corecrm.service.impl;

import com.pm.corecrm.domain.dto.task.AssignTaskRequest;
import com.pm.corecrm.domain.dto.task.CreateTaskRequest;
import com.pm.corecrm.domain.dto.task.TaskDto;
import com.pm.corecrm.domain.dto.task.UpdateTaskRequest;
import com.pm.corecrm.domain.entity.Building;
import com.pm.corecrm.domain.entity.Task;
import com.pm.corecrm.domain.entity.User;
import com.pm.corecrm.mapper.TaskMapper;
import com.pm.corecrm.repository.BuildingRepository;
import com.pm.corecrm.repository.TaskRepository;
import com.pm.corecrm.repository.UserRepository;
import com.pm.corecrm.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final BuildingRepository buildingRepository;
    private final TaskMapper taskMapper;


    @Override
    public TaskDto createTask(CreateTaskRequest request) {
        Task task = taskMapper.toEntity(request);
        User assignee = userRepository.findById(request.getAssigneeId())
                .orElseThrow(() ->
                        new RuntimeException("User not found with id " + request.getAssigneeId()));
        task.setAssignee(assignee);
        if (request.getBuildingId() != null) {
            Building building = buildingRepository.findById(BigDecimal.valueOf(request.getBuildingId()))
                    .orElseThrow(()-> new RuntimeException("Building not found with id "  + request.getBuildingId()));
            task.setBuilding(building);
        }
        Task save = taskRepository.save(task);
        return taskMapper.toDto(save);

    }

    @Override
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Task not found with id " + id));
        return taskMapper.toDto(task);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getTasksByAssignee(Long userId) {
        return taskRepository.findByAssigneeId(userId)
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getTasksByStatus(Task.TaskStatus status) {
        return taskRepository.findByStatus(status)
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getTasksByBuilding(Long buildingId) {
        return taskRepository.findByBuildingId(BigDecimal.valueOf(buildingId))
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto updateTask(Long id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Task not found with id " + id));
         taskMapper.updateEntity(request, task);

        if (request.getAssigneeId() != null) {
            User assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("User not found with id " + request.getAssigneeId()));
            task.setAssignee(assignee);
        }

            if (request.getBuildingId() != null) {
                Building building = buildingRepository.findById(BigDecimal.valueOf(request.getBuildingId()))
                        .orElseThrow(() -> new RuntimeException("Building not found with id " + request.getBuildingId()));
                task.setBuilding(building);
            }


        Task save = taskRepository.save(task);
        return taskMapper.toDto(save);
    }

    @Override
    public TaskDto assignTask(Long id, AssignTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        User assignee = userRepository.findById(request.getAssigneeId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getAssigneeId()));

        task.setAssignee(assignee);
        task.setStatus(Task.TaskStatus.ASSIGNED);

        Task saved = taskRepository.save(task);
        return taskMapper.toDto(saved);
    }

    @Override
    public TaskDto updateStatus(Long id, Task.TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        task.setStatus(status);
        Task saved = taskRepository.save(task);
        return taskMapper.toDto(saved);
    }

    @Override
    public void deleteTask(Long id) {
        if(!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id" + id);
        }
        taskRepository.deleteById(id);

    }
}
