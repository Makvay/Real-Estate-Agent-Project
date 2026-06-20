package com.pm.corecrm.repository;

import com.pm.corecrm.domain.entity.Task;
import com.pm.corecrm.domain.entity.Task.TaskStatus;
import com.pm.corecrm.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long>, JpaSpecificationExecutor<Task> {

    List<Task> findByAssigneeId(Long id);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByBuildingId(BigDecimal buildingId);
}
