package com.RavenDev.motor_misiones_y_Recompensas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(QuestNotFoundException.class)
    public ResponseEntity< Map< String, Object > > handleQuestNotFound(QuestNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "error", "Recurso no encontrado",
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(InvalidQuestStateException.class)
    public ResponseEntity< Map< String, Object > > handleInvalidState(InvalidQuestStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", "Estado de misión inválido",
                "message", ex.getMessage()
        ));
    }
}
