package com.pm.corecrm.repository;

import com.pm.corecrm.domain.entity.Task;
import com.pm.corecrm.domain.entity.Task.TaskStatus;
import com.pm.corecrm.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    // Поиск task по id исполнителя
    List<Task> findByAssigneeId(Long id);
    // Поиск по статусу
    List<Task> findByStatus(TaskStatus status);
    // Поиск задач по ID здания (Building)
    List<Task> findByBuildingId(Long buildingId);
}
