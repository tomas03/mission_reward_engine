package com.RavenDev.motor_misiones_y_Recompensas.dto;

import com.RavenDev.motor_misiones_y_Recompensas.domain.enums.QuestStatus;

public record UserProgressResponseDTO(
        Long progressId,
        Long userId,
        Long questId,
        String questTitle,
        Integer currentAmount,
        Integer targetAmount,
        QuestStatus status
) {}
