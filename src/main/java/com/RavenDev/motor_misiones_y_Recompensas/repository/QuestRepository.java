package com.RavenDev.motor_misiones_y_Recompensas.repository;

import com.RavenDev.motor_misiones_y_Recompensas.domain.Quest;
import com.RavenDev.motor_misiones_y_Recompensas.domain.enums.QuestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestRepository extends JpaRepository<Quest,Long>{
}
