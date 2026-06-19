package com.pm.corecrm.domain.dto.building;

import com.pm.corecrm.domain.entity.Building;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingDto {
    private BigDecimal id;
    private String cadastrNumber;
    private Long price;
    private String address;
    private String ownerName;
    private String ownerPhone;
    private String ownerEmail;
    private Double squareBuilding;
    private String description;
    private Building.BuildingStatus status;
    private Long responsibleManagerId; // ID менеджера


}