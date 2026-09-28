package com.RavenDev.motor_misiones_y_Recompensas.service;

import com.RavenDev.motor_misiones_y_Recompensas.domain.Quest;
import com.RavenDev.motor_misiones_y_Recompensas.domain.UserQuestProgress;
import com.RavenDev.motor_misiones_y_Recompensas.domain.enums.QuestStatus;
import com.RavenDev.motor_misiones_y_Recompensas.domain.enums.QuestType;
import com.RavenDev.motor_misiones_y_Recompensas.event.GameActionEvent;
import com.RavenDev.motor_misiones_y_Recompensas.exception.InvalidQuestStateException;
import com.RavenDev.motor_misiones_y_Recompensas.exception.QuestNotFoundException;
import com.RavenDev.motor_misiones_y_Recompensas.repository.QuestRepository;
import com.RavenDev.motor_misiones_y_Recompensas.repository.UserQuestProgressRepository;
import com.RavenDev.motor_misiones_y_Recompensas.service.strategy.QuestProgressStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class QuestEngineService {
    private final QuestRepository questRepository;
    private final UserQuestProgressRepository progressRepository;
    private final Map< QuestType, QuestProgressStrategy> strategies;

    public QuestEngineService(
            QuestRepository questRepository,
            UserQuestProgressRepository progressRepository,
            List< QuestProgressStrategy > strategyList) {
        this.questRepository = questRepository;
        this.progressRepository = progressRepository;
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(QuestProgressStrategy::getSupportedType, Function.identity()));
    }

    // Inicia una misión para un usuario
    @Transactional
    public UserQuestProgress assignQuest(Long userId, Long questId) {
        Quest quest = questRepository.findById(questId)
                .orElseThrow(() -> new QuestNotFoundException("Misión no encontrada con ID: " + questId));

        progressRepository.findByUserIdAndQuestId(userId, questId)
                .ifPresent(p -> {
                    throw new InvalidQuestStateException("El usuario ya tiene asignada esta misión");
                });

        UserQuestProgress progress = new UserQuestProgress();
        progress.setUserId(userId);
        progress.setQuest(quest);
        progress.setCurrentAmount(0);
        progress.setStatus(QuestStatus.IN_PROGRESS);

        return progressRepository.save(progress);
    }

    // Procesa el progreso a partir de un evento del juego
    @Transactional
    public void processEvent(GameActionEvent event) {
        QuestProgressStrategy strategy = strategies.get(event.actionType());
        if (strategy == null) {
            return;
        }

        List< UserQuestProgress > activeQuests = progressRepository
                .findActiveQuestsByUserAndType(event.userId(), QuestStatus.IN_PROGRESS, event.actionType());

        for (UserQuestProgress progress : activeQuests) {
            boolean updated = strategy.evaluate(progress, event);

            if (updated) {
                // Verificar si completó el objetivo
                if (progress.getCurrentAmount() >= progress.getQuest().getTargetAmount()) {
                    progress.setStatus(QuestStatus.COMPLETED);
                    progress.setCompletedAt(LocalDateTime.now());
                }
                progressRepository.save(progress);
            }
        }
    }

    // Reclamar recompensa
    @Transactional
    public UserQuestProgress claimReward(Long userId, Long questId) {
        UserQuestProgress progress = progressRepository.findByUserIdAndQuestId(userId, questId)
                .orElseThrow(() -> new QuestNotFoundException("No se encontró progreso para la misión"));

        if (progress.getStatus() != QuestStatus.COMPLETED) {
            throw new InvalidQuestStateException("La misión no se encuentra completada para reclamar recompensas");
        }

        progress.setStatus(QuestStatus.CLAIMED);
        progress.setClaimedAt(LocalDateTime.now());

        return progressRepository.save(progress);
    }
}
