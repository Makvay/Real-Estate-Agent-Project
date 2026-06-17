package com.pm.corecrm.repository;

import com.pm.corecrm.domain.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository {
    List<Task> findByAssigneeId(Long id);
    List<Task> findByStatus(TaskStatus status);


}
