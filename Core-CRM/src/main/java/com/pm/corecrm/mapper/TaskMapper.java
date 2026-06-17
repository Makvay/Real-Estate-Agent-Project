package com.pm.corecrm.mapper;

import com.pm.corecrm.domain.entity.Task;
import com.pm.corecrm.domain.dto.task.CreateTaskRequest;
import com.pm.corecrm.domain.dto.task.TaskDto;
import com.pm.corecrm.domain.dto.task.UpdateTaskRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(CreateTaskRequest request);

    TaskDto toDto(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateTaskRequest request, @MappingTarget Task task);
}