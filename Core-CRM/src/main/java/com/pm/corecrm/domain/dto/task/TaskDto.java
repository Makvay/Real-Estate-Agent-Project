package com.pm.corecrm.dto;

import com.pm.corecrm.domain.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Task.TaskStatus status;
    private Long assigneeId; // ID исполнителя
    private String assigneeName; // Имя исполнителя
    private Long buildingId; // ID здания
    private String buildingAddress; // Адрес здания
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}