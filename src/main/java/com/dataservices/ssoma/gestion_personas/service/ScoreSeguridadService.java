package com.dataservices.ssoma.gestion_personas.service;

import com.dataservices.ssoma.gestion_personas.dto.ScoreSeguridadDTO;

import java.util.List;
import java.util.UUID;

public interface ScoreSeguridadService {

    ScoreSeguridadDTO crearScoreSeguridad(ScoreSeguridadDTO scoreSeguridadDTO);

    ScoreSeguridadDTO obtenerScoreSeguridadPorId(UUID scoreId);

    List<ScoreSeguridadDTO> obtenerHistorialScoresPorPersona(UUID personaId);

    ScoreSeguridadDTO obtenerUltimoScorePorPersona(UUID personaId);

    ScoreSeguridadDTO actualizarScoreSeguridad(UUID scoreId, ScoreSeguridadDTO scoreSeguridadDTO);

    void eliminarScoreSeguridad(UUID scoreId);

    Double calcularPromedioScorePersona(UUID personaId);
}
