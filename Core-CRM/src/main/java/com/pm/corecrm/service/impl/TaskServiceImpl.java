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
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final BuildingRepository buildingRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDto createTask(CreateTaskRequest request) {
        User assignee = userRepository.findById(request.getAssigneeId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getAssigneeId()));

        Building building = null;
        if (request.getBuildingId() != null) {
            BigDecimal buildingId = BigDecimal.valueOf(request.getBuildingId());
            building = buildingRepository.findById(buildingId)
                    .orElseThrow(() -> new RuntimeException("Building not found with id: " + buildingId));
        }

        Task task = taskMapper.toEntity(request);
        task.setAssignee(assignee);
        task.setBuilding(building);

        if (request.getStatus() == null) {
            task.setStatus(Task.TaskStatus.NEW);
        }

        Task saved = taskRepository.save(task);
        return taskMapper.toDto(saved);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        return taskMapper.toDto(task);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TaskDto> getAllTasks(Task.TaskStatus status, Long assigneeId, Long buildingId, Pageable pageable) {
        Specification<Task> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (assigneeId != null) {
                predicates.add(cb.equal(root.get("assignee").get("id"), assigneeId));
            }
            if (buildingId != null) {
                predicates.add(cb.equal(root.get("building").get("id"), buildingId));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return taskRepository.findAll(spec, pageable).map(taskMapper::toDto);
    }

    @Override
    public List<TaskDto> getTasksByAssignee(Long userId) {
        return taskRepository.findByAssigneeId(userId).stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getTasksByStatus(Task.TaskStatus status) {
        return taskRepository.findByStatus(status).stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getTasksByBuilding(Long buildingId) {
        return taskRepository.findByBuildingId(BigDecimal.valueOf(buildingId)).stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto updateTask(Long id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        if (request.getAssigneeId() != null) {
            User assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getAssigneeId()));
            task.setAssignee(assignee);
        }

        if (request.getBuildingId() != null) {
            Building building = buildingRepository.findById(BigDecimal.valueOf(request.getBuildingId()))
                    .orElseThrow(() -> new RuntimeException("Building not found with id: " + request.getBuildingId()));
            task.setBuilding(building);
        }

        taskMapper.updateEntity(request, task);
        Task updated = taskRepository.save(task);
        return taskMapper.toDto(updated);
    }

    @Override
    public TaskDto assignTask(Long id, AssignTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        User assignee = userRepository.findById(request.getAssigneeId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getAssigneeId()));

        task.setAssignee(assignee);
        task.setStatus(Task.TaskStatus.ASSIGNED);

        Task updated = taskRepository.save(task);
        return taskMapper.toDto(updated);
    }

    @Override
    public TaskDto updateStatus(Long id, Task.TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        task.setStatus(status);
        Task updated = taskRepository.save(task);
        return taskMapper.toDto(updated);
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}
