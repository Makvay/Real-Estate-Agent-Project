package com.pm.corecrm.mapper;

import com.pm.corecrm.domain.entity.Building;
import com.pm.corecrm.domain.dto.building.BuildingDto;
import com.pm.corecrm.domain.dto.building.CreateBuildingRequest;
import com.pm.corecrm.domain.dto.building.UpdateBuildingRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BuildingMapper {

    Building toEntity(CreateBuildingRequest request);

    BuildingDto toDto(Building building);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateBuildingRequest request, @MappingTarget Building building);
}