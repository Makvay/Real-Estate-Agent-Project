package com.pm.corecrm.dto;

import com.pm.corecrm.domain.entity.Task;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskRequest {
    private String title;
    private String description;

    @FutureOrPresent(message = "Дата должна быть в настоящем или будущем")
    private LocalDate dueDate;

    private Task.TaskStatus status;
    private Long assigneeId;
    private Long buildingId;
}