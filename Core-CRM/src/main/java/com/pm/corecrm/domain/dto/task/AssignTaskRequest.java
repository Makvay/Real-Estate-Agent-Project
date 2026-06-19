package com.pm.corecrm.domain.dto.task;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignTaskRequest {

    @NotNull(message = "ID исполнителя обязателен")
    private Long assigneeId;
}