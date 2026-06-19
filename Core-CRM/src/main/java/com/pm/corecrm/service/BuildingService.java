package com.pm.corecrm.service;

import com.pm.corecrm.domain.dto.building.BuildingDto;
import com.pm.corecrm.domain.dto.building.CreateBuildingRequest;
import com.pm.corecrm.domain.dto.building.UpdateBuildingRequest;

import java.math.BigDecimal;
import java.util.List;

public interface BuildingService {

    BuildingDto createBuilding(CreateBuildingRequest request);
    BuildingDto getBuildingById(BigDecimal id);
    List<BuildingDto> getAllBuildings();
    BuildingDto updateBuilding(BigDecimal id, UpdateBuildingRequest request);
    void DeleteBuilding(BigDecimal id);
    BuildingDto assignManager(BigDecimal id, Long managerId);


}
