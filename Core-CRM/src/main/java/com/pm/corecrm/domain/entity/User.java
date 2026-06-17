package com.pm.corecrm.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@ToString
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<Task> tasks;



}
