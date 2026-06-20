package com.pm.corecrm.domain.entity;

import com.pm.corecrm.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "buildings")
public class Building {
    @Id
    private BigDecimal id;
    private String cadastrNumber;
    private Long price;
    private String address;
    private String ownerName;
    private String ownerPhone;
    private String ownerEmail;
    private Double squareBuilding;
    private String description;

    @Enumerated(EnumType.STRING)
    private BuildingStatus status;

    @ManyToOne
    @JoinColumn(name = "responsible_manager_id")
    private User responsibleManager;


    public enum BuildingStatus   {
        SOLD, NOT_ASSIGNED , ASSIGNED
    }





}



