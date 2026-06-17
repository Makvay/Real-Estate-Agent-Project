package com.pm.corecrm.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "buildings")
public class Build {
    @Id
    private Long id;
    private String cadastNumber;
    private String address;
    private String ownerName;
    private String ownerPhone;
    private String ownerEmail;
    





}
