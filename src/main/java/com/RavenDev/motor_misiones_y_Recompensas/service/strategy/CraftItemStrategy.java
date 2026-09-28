package com.RavenDev.motor_misiones_y_Recompensas.service.strategy;

import com.RavenDev.motor_misiones_y_Recompensas.domain.UserQuestProgress;
import com.RavenDev.motor_misiones_y_Recompensas.domain.enums.QuestType;
import com.RavenDev.motor_misiones_y_Recompensas.event.GameActionEvent;
import org.springframework.stereotype.Component;

@Component
public class CraftItemStrategy implements QuestProgressStrategy {
    @Override
    public QuestType getSupportedType() {
        return QuestType.CRAFT_ITEM;
    }

    @Override
    public boolean evaluate(UserQuestProgress progress, GameActionEvent event) {
        String requiredIdentifier = progress.getQuest().getTargetIdentifier();
        if(requiredIdentifier != null && !requiredIdentifier.equalsIgnoreCase(event.targetIdentifier())){
            return false;
        }
        int newAmount = progress.getCurrentAmount() + event.amount();
        progress.setCurrentAmount(newAmount);
        return true;
    }
}
