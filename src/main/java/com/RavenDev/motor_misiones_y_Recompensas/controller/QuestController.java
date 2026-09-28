package com.RavenDev.motor_misiones_y_Recompensas.controller;

import com.RavenDev.motor_misiones_y_Recompensas.domain.UserQuestProgress;
import com.RavenDev.motor_misiones_y_Recompensas.dto.QuestRequestDTO;
import com.RavenDev.motor_misiones_y_Recompensas.dto.QuestResponseDTO;
import com.RavenDev.motor_misiones_y_Recompensas.dto.UserProgressResponseDTO;
import com.RavenDev.motor_misiones_y_Recompensas.event.GameActionEvent;
import com.RavenDev.motor_misiones_y_Recompensas.service.QuestEngineService;
import com.RavenDev.motor_misiones_y_Recompensas.service.QuestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quests")
public class QuestController {
    private final QuestService questService;
    private final QuestEngineService engineService;

    public QuestController(QuestService questService, QuestEngineService engineService) {
        this.questService = questService;
        this.engineService = engineService;
    }

    // 1. Crear una nueva plantilla de misión
    @PostMapping
    public ResponseEntity< QuestResponseDTO > createQuest(@Valid @RequestBody QuestRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questService.createQuest(dto));
    }

    // 2. Listar todas las misiones
    @GetMapping
    public ResponseEntity< List< QuestResponseDTO > > getAllQuests() {
        return ResponseEntity.ok(questService.getAllQuests());
    }

    // 3. Asignar misión a un usuario
    @PostMapping("/{questId}/assign/{userId}")
    public ResponseEntity< UserProgressResponseDTO > assignQuest(
            @PathVariable Long questId,
            @PathVariable Long userId) {
        UserQuestProgress progress = engineService.assignQuest(userId, questId);
        return ResponseEntity.status(HttpStatus.CREATED).body(toProgressDTO(progress));
    }

    // 4. Emitir acción de juego para avanzar misiones
    @PostMapping("/events")
    public ResponseEntity< String > sendGameEvent(@RequestBody GameActionEvent event) {
        engineService.processEvent(event);
        return ResponseEntity.ok("Evento procesado correctamente");
    }

    // 5. Reclamar recompensa de misión completada
    @PostMapping("/{questId}/claim/{userId}")
    public ResponseEntity< UserProgressResponseDTO > claimReward(
            @PathVariable Long questId,
            @PathVariable Long userId) {
        UserQuestProgress progress = engineService.claimReward(userId, questId);
        return ResponseEntity.ok(toProgressDTO(progress));
    }

    private UserProgressResponseDTO toProgressDTO(UserQuestProgress p) {
        return new UserProgressResponseDTO(
                p.getId(),
                p.getUserId(),
                p.getQuest().getId(),
                p.getQuest().getTitle(),
                p.getCurrentAmount(),
                p.getQuest().getTargetAmount(),
                p.getStatus()
        );
    }
}
