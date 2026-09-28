package com.RavenDev.motor_misiones_y_Recompensas.service.strategy;


import com.RavenDev.motor_misiones_y_Recompensas.domain.UserQuestProgress;
import com.RavenDev.motor_misiones_y_Recompensas.domain.enums.QuestType;
import com.RavenDev.motor_misiones_y_Recompensas.event.GameActionEvent;
import org.springframework.stereotype.Component;

@Component
public class DefeatEnemyStrategy implements QuestProgressStrategy {
    @Override
    public QuestType getSupportedType() {
        return QuestType.DEFEAT_ENEMY;
    }

    @Override
    public boolean evaluate(UserQuestProgress progress, GameActionEvent event) {
        String requiredEnemy = progress.getQuest().getTargetIdentifier();

        if (requiredEnemy != null && !requiredEnemy.equalsIgnoreCase(event.targetIdentifier())) {
            return false;
        }

        int newAmount = progress.getCurrentAmount() + event.amount();
        progress.setCurrentAmount(newAmount);
        return true;
    }
}
