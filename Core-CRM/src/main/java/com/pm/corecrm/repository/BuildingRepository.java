package com.pm.corecrm.repository;

import com.pm.corecrm.domain.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pm.corecrm.domain.entity.Building.BuildingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, BigDecimal> {

    Optional<Building> findByCadastrNumber(String cadastrNumber);

    List<Building> findByStatus(BuildingStatus status);

    Page<Building> findByStatus(BuildingStatus status, Pageable pageable);

    Page<Building> findByPriceBetween(Long minPrice, Long maxPrice, Pageable pageable);

    Page<Building> findByStatusAndPriceBetween(BuildingStatus status, Long minPrice, Long maxPrice, Pageable pageable);

    @Query(value = "select id from building", nativeQuery = true)
    List<Long> findAllIds();

}
