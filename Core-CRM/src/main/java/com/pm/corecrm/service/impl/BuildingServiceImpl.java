package com.pm.corecrm.service.impl;

import com.pm.corecrm.domain.dto.building.BuildingDto;
import com.pm.corecrm.domain.dto.building.CreateBuildingRequest;
import com.pm.corecrm.domain.dto.building.UpdateBuildingRequest;
import com.pm.corecrm.domain.entity.Building;
import com.pm.corecrm.domain.entity.User;
import com.pm.corecrm.mapper.BuildingMapper;
import com.pm.corecrm.repository.BuildingRepository;
import com.pm.corecrm.repository.UserRepository;
import com.pm.corecrm.service.BuildingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository buildingRepository;
    private final UserRepository userRepository;
    private final BuildingMapper buildingMapper;

    @Override
    public BuildingDto createBuilding(CreateBuildingRequest request) {
        if (buildingRepository.findByCadastrNumber(request.getCadastrNumber()).isPresent()) {
            throw new RuntimeException("Building with cadastral number " + request.getCadastrNumber()
                    + "already exists");
        }
        Building building = buildingMapper.toEntity(request);

        if (request.getResponsibleManagerId() != null) {
            User manager = userRepository.findById(request.getResponsibleManagerId())
                    .orElseThrow(()-> new RuntimeException("Manager not found with id: "  +
                            request.getResponsibleManagerId()));
            building.setResponsibleManager(manager);
        }
        Building saved = buildingRepository.save(building);
        return buildingMapper.toDto(saved);

    }

    @Override
    public BuildingDto getBuildingById(BigDecimal id) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Building not found with id: " + id));
        return buildingMapper.toDto(building);
    }

    @Override
    public List<BuildingDto> getAllBuildings() {
        return buildingRepository.findAll().stream()
                .map(buildingMapper::toDto)
                .collect(Collectors.toList());
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
