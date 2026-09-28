package com.RavenDev.motor_misiones_y_Recompensas.service;

import com.RavenDev.motor_misiones_y_Recompensas.domain.Quest;
import com.RavenDev.motor_misiones_y_Recompensas.dto.QuestRequestDTO;
import com.RavenDev.motor_misiones_y_Recompensas.dto.QuestResponseDTO;
import com.RavenDev.motor_misiones_y_Recompensas.repository.QuestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestService {
    private final QuestRepository questRepository;

    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    @Transactional
    public QuestResponseDTO createQuest(QuestRequestDTO dto) {
        Quest quest = new Quest();
        quest.setTitle(dto.title());
        quest.setDescription(dto.description());
        quest.setQuestType(dto.questType());
        quest.setTargetIdentifier(dto.targetIdentifier());
        quest.setTargetAmount(dto.targetAmount());
        quest.setRewardExp(dto.rewardExp());
        quest.setRewardGold(dto.rewardGold());

        Quest saved = questRepository.save(quest);
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List< QuestResponseDTO > getAllQuests() {
        return questRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    private QuestResponseDTO toDTO(Quest q) {
        return new QuestResponseDTO(
                q.getId(),
                q.getTitle(),
                q.getDescription(),
                q.getQuestType(),
                q.getTargetIdentifier(),
                q.getTargetAmount(),
                q.getRewardExp(),
                q.getRewardGold()
        );
    }
}
