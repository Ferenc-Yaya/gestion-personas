package com.dataservices.ssoma.gestion_personas.repository;

import com.dataservices.ssoma.gestion_personas.entity.ScoreSeguridad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScoreSeguridadRepository extends JpaRepository<ScoreSeguridad, UUID> {

    List<ScoreSeguridad> findByPersonaIdOrderByFechaEvaluacionDesc(UUID personaId);

    @Query("SELECT s FROM ScoreSeguridad s WHERE s.personaId = :personaId ORDER BY s.fechaEvaluacion DESC LIMIT 1")
    Optional<ScoreSeguridad> findUltimoScoreByPersonaId(@Param("personaId") UUID personaId);

    void deleteByPersonaId(UUID personaId);
}
