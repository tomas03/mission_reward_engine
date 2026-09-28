package com.RavenDev.motor_misiones_y_Recompensas.event;

import com.RavenDev.motor_misiones_y_Recompensas.domain.enums.QuestType;

public record GameActionEvent(
        Long userId,
        QuestType actionType,
        String targetIdentifier,
        int amount
) {}
