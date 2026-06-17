package com.pm.corecrm.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

//  @Data - проблемы со string
@RequiredArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @FutureOrPresent
    private LocalDate dueDate;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignee;

    @OneToOne
    @JoinColumn(name = "building_id")
    private Building building;

    public enum TaskStatus   {
        NEW, ASSIGNED, ARCHIVED, FINISHED, CANCELLED
    }



}
