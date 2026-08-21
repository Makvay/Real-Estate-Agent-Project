package com.pm.corecrm.domain.dto.building;
import com.pm.corecrm.domain.entity.Building.BuildingStatus;
import lombok.Data;

@Data
public class BuildingMessage {
    private String cadastrNumber;
    private Long price;
    private Double squareBuilding;
    private String address;
    private String description;
    private String source;
    private BuildingStatus status;
}
