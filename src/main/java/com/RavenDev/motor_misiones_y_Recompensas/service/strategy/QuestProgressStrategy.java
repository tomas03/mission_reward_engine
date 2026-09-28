package com.RavenDev.motor_misiones_y_Recompensas.service.strategy;

import com.RavenDev.motor_misiones_y_Recompensas.domain.UserQuestProgress;
import com.RavenDev.motor_misiones_y_Recompensas.domain.enums.QuestType;
import com.RavenDev.motor_misiones_y_Recompensas.event.GameActionEvent;

public interface QuestProgressStrategy {
    QuestType getSupportedType();
    boolean evaluate(UserQuestProgress progress, GameActionEvent event);
}
