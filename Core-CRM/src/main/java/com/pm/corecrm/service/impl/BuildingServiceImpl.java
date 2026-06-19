package com.pm.corecrm.service.impl;

import com.pm.corecrm.domain.dto.building.BuildingDto;
import com.pm.corecrm.domain.dto.building.CreateBuildingRequest;
import com.pm.corecrm.domain.dto.building.UpdateBuildingRequest;
import com.pm.corecrm.domain.entity.Building;
import com.pm.corecrm.service.BuildingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {
    @Override
    public BuildingDto createBuilding(CreateBuildingRequest request) {
        return null;
    }

    @Override
    public BuildingDto getBuildingById(BigDecimal id) {
        return null;
    }

    @Override
    public List<BuildingDto> getAllBuildings() {
        return List.of();
    }

    @Override
    public BuildingDto updateBuilding(BigDecimal id, UpdateBuildingRequest request) {
        return null;
    }

    @Override
    public void deleteBuilding(BigDecimal id) {

    }

    @Override
    public BuildingDto assignManager(BigDecimal id, Long managerId) {
        return null;
    }

    @Override
    public List<BuildingDto> getBuildingsByStatus(Building.BuildingStatus status) {
        return List.of();
    }
}
