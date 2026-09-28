package com.RavenDev.motor_misiones_y_Recompensas.repository;

import com.RavenDev.motor_misiones_y_Recompensas.domain.UserQuestProgress;
import com.RavenDev.motor_misiones_y_Recompensas.domain.enums.QuestStatus;
import com.RavenDev.motor_misiones_y_Recompensas.domain.enums.QuestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserQuestProgressRepository extends JpaRepository<UserQuestProgress,Long> {
    Optional<UserQuestProgress>findByUserIdAndQuestId(Long UserId,Long questId);
    List<UserQuestProgress> findByUserId(Long userId);
    @Query("SELECT p FROM UserQuestProgress p JOIN FETCH p.quest q " +
            "WHERE p.userId = :userId AND p.status = :status AND q.questType = :questType")
    List<UserQuestProgress>findActiveQuestsByUserAndType(
            @Param("userId") Long userId,
            @Param("status") QuestStatus status,
            @Param("questType") QuestType questType
    );
}
