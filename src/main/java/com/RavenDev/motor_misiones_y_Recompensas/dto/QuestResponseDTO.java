package com.RavenDev.motor_misiones_y_Recompensas.dto;

import com.RavenDev.motor_misiones_y_Recompensas.domain.enums.QuestType;

public record QuestResponseDTO(
        Long id,
        String title,
        String description,
        QuestType questType,
        String targetIdentifier,
        Integer targetAmount,
        Integer rewardExp,
        Integer rewardGold
) {}
