package com.pm.corecrm.domain.dto.task;

import com.pm.corecrm.domain.entity.Task;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskRequest {

    @NotBlank(message = "Заголовок обязателен")
    private String title;

    private String description;

    @NotNull(message = "Дата выполнения обязательна")
    @FutureOrPresent(message = "Дата должна быть в настоящем или будущем")
    private LocalDate dueDate;

    private Task.TaskStatus status;

    @NotNull(message = "ID исполнителя обязателен")
    private Long assigneeId;

    private Long buildingId; // опционально
}