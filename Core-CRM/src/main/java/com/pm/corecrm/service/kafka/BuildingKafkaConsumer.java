package com.pm.corecrm.service.kafka;

import com.pm.corecrm.domain.dto.building.BuildingMessage;
import com.pm.corecrm.domain.entity.Building;
import com.pm.corecrm.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuildingKafkaConsumer {

    private final BuildingRepository buildingRepository;

    @KafkaListener(topics = "building.commands")
    public void consume(BuildingMessage msg) {
        Building building = new Building();
        building.setId(System.currentTimeMillis());
        building.setCadastrNumber(msg.getCadastrNumber());
        building.setPrice(msg.getPrice());
        building.setSquareBuilding(msg.getSquareBuilding());
        building.setDescription(msg.getDescription());
        building.setStatus(msg.getStatus());
        buildingRepository.save(building);
    }


}
