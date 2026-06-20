package com.pm.corecrm.controller;

import com.pm.corecrm.domain.dto.building.BuildingDto;
import com.pm.corecrm.domain.dto.building.CreateBuildingRequest;
import com.pm.corecrm.domain.dto.building.UpdateBuildingRequest;
import com.pm.corecrm.domain.entity.Building;
import com.pm.corecrm.service.BuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/buildings")
@Tag(name = "Building Management", description = "API для управления объектами недвижимости")
public class BuildingController {

    private final BuildingService buildingService;

    @PostMapping
    @Operation(summary = "Создать новый объект недвижимости")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Объект успешно создан"),
            @ApiResponse(responseCode = "400", description = "Неверные данные запроса"),
            @ApiResponse(responseCode = "409", description = "Объект с таким кадастровым номером уже существует"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<BuildingDto> createBuilding(@Valid @RequestBody CreateBuildingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(buildingService.createBuilding(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить объект по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Объект найден"),
            @ApiResponse(responseCode = "404", description = "Объект не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<BuildingDto> getBuildingById(@PathVariable BigDecimal id) {
        return ResponseEntity.ok(buildingService.getBuildingById(id));
    }

    @GetMapping
    @Operation(summary = "Получить все объекты с фильтрами (пагинация)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка объектов"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<Page<BuildingDto>> getAllBuildings(
            @RequestParam(required = false) Building.BuildingStatus status,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(buildingService.getAllBuildings(status, minPrice, maxPrice, pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить объект недвижимости")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Объект успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Объект не найден"),
            @ApiResponse(responseCode = "400", description = "Неверные данные запроса"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<BuildingDto> updateBuilding(
            @PathVariable BigDecimal id,
            @Valid @RequestBody UpdateBuildingRequest request) {
        return ResponseEntity.ok(buildingService.updateBuilding(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить объект недвижимости")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Объект успешно удален"),
            @ApiResponse(responseCode = "404", description = "Объект не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<Void> deleteBuilding(@PathVariable BigDecimal id) {
        buildingService.deleteBuilding(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/assign/{managerId}")
    @Operation(summary = "Назначить менеджера на объект")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Менеджер успешно назначен"),
            @ApiResponse(responseCode = "404", description = "Объект или менеджер не найдены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<BuildingDto> assignManager(
            @PathVariable BigDecimal id,
            @PathVariable Long managerId) {
        return ResponseEntity.ok(buildingService.assignManager(id, managerId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Получить объекты по статусу")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка объектов"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<List<BuildingDto>> getBuildingsByStatus(@PathVariable Building.BuildingStatus status) {
        return ResponseEntity.ok(buildingService.getBuildingsByStatus(status));
    }
}
