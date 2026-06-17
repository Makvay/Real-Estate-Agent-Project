package com.pm.corecrm.repository;

import com.pm.corecrm.domain.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, BigDecimal> {

    Optional<Building> findByCadastrNumber(String cadastrNumber);
    List<Building> findByStatus(Building.BuildingStatus status);



}
