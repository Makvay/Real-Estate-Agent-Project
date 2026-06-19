package com.pm.corecrm.domain.dto.building;

import com.pm.corecrm.domain.entity.Building;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBuildingRequest {

    @NotBlank(message = "Кадастровый номер обязателен")
    private String cadastrNumber;

    @NotNull(message = "Цена обязательна")
    @Positive(message = "Цена должна быть положительной")
    private Long price;

    @NotBlank(message = "Адрес обязателен")
    private String address;

    private String ownerName;
    private String ownerPhone;
    private String ownerEmail;

    @Positive(message = "Площадь должна быть положительной")
    private Double squareBuilding;

    private String description;

    private Building.BuildingStatus status;

    private Long responsibleManagerId;
}