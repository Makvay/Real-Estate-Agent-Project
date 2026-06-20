package com.pm.corecrm.service;

import com.pm.corecrm.domain.dto.building.BuildingDto;
import com.pm.corecrm.domain.dto.building.CreateBuildingRequest;
import com.pm.corecrm.domain.dto.building.UpdateBuildingRequest;
import com.pm.corecrm.domain.entity.Building;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface BuildingService {

    BuildingDto createBuilding(CreateBuildingRequest request);
    BuildingDto getBuildingById(BigDecimal id);
    List<BuildingDto> getAllBuildings();
    BuildingDto updateBuilding(BigDecimal id, UpdateBuildingRequest request);
    void deleteBuilding(BigDecimal id);
    BuildingDto assignManager(BigDecimal id, Long managerId);
    List<BuildingDto> getBuildingsByStatus(Building.BuildingStatus status);
    Page<BuildingDto> getAllBuildings(Building.BuildingStatus status, Long minPrice, Long maxPrice, Pageable pageable);
    Page<BuildingDto> getAllBuildings(Pageable pageable);


}
