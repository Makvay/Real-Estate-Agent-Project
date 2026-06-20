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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            throw new RuntimeException("Building with cadastral number " + request.getCadastrNumber() + " already exists");
        }
        Building building = buildingMapper.toEntity(request);

        if (request.getResponsibleManagerId() != null) {
            User manager = userRepository.findById(request.getResponsibleManagerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found with id: " + request.getResponsibleManagerId()));
            building.setResponsibleManager(manager);
        }
        Building saved = buildingRepository.save(building);
        return buildingMapper.toDto(saved);
    }

    @Override
    public BuildingDto getBuildingById(BigDecimal id) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Building not found with id: " + id));
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
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Building not found with id: " + id));

        buildingMapper.updateEntity(request, building);
        if (request.getResponsibleManagerId() != null) {
            User manager = userRepository.findById(request.getResponsibleManagerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found with id: " + request.getResponsibleManagerId()));
            building.setResponsibleManager(manager);
        }
        Building updated = buildingRepository.save(building);
        return buildingMapper.toDto(updated);
    }

    @Override
    public void deleteBuilding(BigDecimal id) {
        if (!buildingRepository.existsById(id)) {
            throw new RuntimeException("Building not found with id: " + id);
        }
        buildingRepository.deleteById(id);
    }

    @Override
    public BuildingDto assignManager(BigDecimal id, Long managerId) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Building not found with id: " + id));

        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + managerId));
        building.setResponsibleManager(manager);
        building.setStatus(Building.BuildingStatus.ASSIGNED);

        Building updated = buildingRepository.save(building);
        return buildingMapper.toDto(updated);
    }

    @Override
    public List<BuildingDto> getBuildingsByStatus(Building.BuildingStatus status) {
        return buildingRepository.findByStatus(status).stream()
                .map(buildingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BuildingDto> getAllBuildings(Pageable pageable) {
        return buildingRepository.findAll(pageable)
                .map(buildingMapper::toDto);
    }

    @Override
    public Page<BuildingDto> getAllBuildings(Building.BuildingStatus status, Long minPrice, Long maxPrice, Pageable pageable) {
        if (status != null && minPrice != null && maxPrice != null) {
            return buildingRepository.findByStatusAndPriceBetween(status, minPrice, maxPrice, pageable)
                    .map(buildingMapper::toDto);
        } else if (status != null) {
            return buildingRepository.findByStatus(status, pageable)
                    .map(buildingMapper::toDto);
        } else if (minPrice != null && maxPrice != null) {
            return buildingRepository.findByPriceBetween(minPrice, maxPrice, pageable)
                    .map(buildingMapper::toDto);
        } else {
            return buildingRepository.findAll(pageable)
                    .map(buildingMapper::toDto);
        }
    }
}