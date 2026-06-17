package com.pm.corecrm.domain.dto.building;

import com.pm.corecrm.domain.entity.Building;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBuildingRequest {
    private Long price;
    private String address;
    private String ownerName;
    private String ownerPhone;
    private String ownerEmail;

    @Positive(message = "Площадь должна быть положительной")
    private Double squareBuilding;

    private String description;
    private Building.BuildingStatus status;
    private Long responsibleManagerId;
}