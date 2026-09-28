package com.RavenDev.motor_misiones_y_Recompensas.dto;

import com.RavenDev.motor_misiones_y_Recompensas.domain.enums.QuestType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestRequestDTO(
        @NotBlank(message = "El título es obligatorio")
        String title,
        @NotBlank(message = "La descripción es obligatoria")
        String description,
        @NotNull(message = "El tipo de misión es obligatorio")
        QuestType questType,
        String targetIdentifier,
        @NotNull @Min(1)
        Integer targetAmount,
        @NotNull @Min(0)
        Integer rewardExp,
        @NotNull @Min(0)
        Integer rewardGold
) {}
