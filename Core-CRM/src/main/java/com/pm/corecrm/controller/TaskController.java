package com.pm.corecrm.controller;

import com.pm.corecrm.domain.dto.task.AssignTaskRequest;
import com.pm.corecrm.domain.dto.task.CreateTaskRequest;
import com.pm.corecrm.domain.dto.task.TaskDto;
import com.pm.corecrm.domain.dto.task.UpdateTaskRequest;
import com.pm.corecrm.domain.entity.Task;
import com.pm.corecrm.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task Management", description = "API для управления задачами")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Создать новую задачу")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Задача успешно создана"),
            @ApiResponse(responseCode = "400", description = "Неверные данные запроса"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody CreateTaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить задачу по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача найдена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping
    @Operation(summary = "Получить все задачи с фильтрами (пагинация)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка задач"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<Page<TaskDto>> getAllTasks(
            @RequestParam(required = false) Task.TaskStatus status,
            @RequestParam(required = false) Long assigneeId,
            @RequestParam(required = false) Long buildingId,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(taskService.getAllTasks(status, assigneeId, buildingId, pageable));
    }

    @GetMapping("/assignee/{userId}")
    @Operation(summary = "Получить задачи по исполнителю")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка задач"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<List<TaskDto>> getTasksByAssignee(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTasksByAssignee(userId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Получить задачи по статусу")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка задач"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<List<TaskDto>> getTasksByStatus(@PathVariable Task.TaskStatus status) {
        return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }

    @GetMapping("/building/{buildingId}")
    @Operation(summary = "Получить задачи по зданию")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка задач"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<List<TaskDto>> getTasksByBuilding(@PathVariable Long buildingId) {
        return ResponseEntity.ok(taskService.getTasksByBuilding(buildingId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить задачу")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "400", description = "Неверные данные запроса"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    @PatchMapping("/{id}/assign")
    @Operation(summary = "Назначить исполнителя на задачу")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Исполнитель успешно назначен"),
            @ApiResponse(responseCode = "404", description = "Задача или исполнитель не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<TaskDto> assignTask(
            @PathVariable Long id,
            @Valid @RequestBody AssignTaskRequest request) {
        return ResponseEntity.ok(taskService.assignTask(id, request));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Обновить статус задачи")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Статус успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<TaskDto> updateStatus(
            @PathVariable Long id,
            @RequestParam Task.TaskStatus status) {
        return ResponseEntity.ok(taskService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить задачу")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Задача успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
