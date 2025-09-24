package com.dataservices.ssoma.gestion_personas.service.impl;

import com.dataservices.ssoma.gestion_personas.dto.ScoreSeguridadDTO;
import com.dataservices.ssoma.gestion_personas.entity.ScoreSeguridad;
import com.dataservices.ssoma.gestion_personas.exception.PersonaNotFoundException;
import com.dataservices.ssoma.gestion_personas.mapper.ScoreSeguridadMapper;
import com.dataservices.ssoma.gestion_personas.repository.ScoreSeguridadRepository;
import com.dataservices.ssoma.gestion_personas.service.ScoreSeguridadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.OptionalDouble;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ScoreSeguridadServiceImpl implements ScoreSeguridadService {

    private final ScoreSeguridadRepository scoreSeguridadRepository;
    private final ScoreSeguridadMapper scoreSeguridadMapper;

    @Override
    public ScoreSeguridadDTO crearScoreSeguridad(ScoreSeguridadDTO scoreSeguridadDTO) {
        log.info("Creando nuevo score de seguridad para persona ID: {} con puntuación: {}",
                scoreSeguridadDTO.getPersonaId(), scoreSeguridadDTO.getPuntuacion());

        ScoreSeguridad scoreSeguridad = scoreSeguridadMapper.toEntity(scoreSeguridadDTO);
        ScoreSeguridad scoreSeguridadGuardado = scoreSeguridadRepository.save(scoreSeguridad);

        log.info("Score de seguridad creado exitosamente con ID: {}", scoreSeguridadGuardado.getScoreId());
        return scoreSeguridadMapper.toDTO(scoreSeguridadGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ScoreSeguridadDTO obtenerScoreSeguridadPorId(UUID scoreId) {
        log.info("Buscando score de seguridad con ID: {}", scoreId);

        ScoreSeguridad scoreSeguridad = scoreSeguridadRepository.findById(scoreId)
                .orElseThrow(() -> new PersonaNotFoundException("Score de seguridad no encontrado con ID: " + scoreId));

        return scoreSeguridadMapper.toDTO(scoreSeguridad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScoreSeguridadDTO> obtenerHistorialScoresPorPersona(UUID personaId) {
        log.info("Obteniendo historial de scores para persona ID: {}", personaId);

        List<ScoreSeguridad> scores = scoreSeguridadRepository.findByPersonaIdOrderByFechaEvaluacionDesc(personaId);

        log.info("Encontrados {} scores para la persona ID: {}", scores.size(), personaId);
        return scoreSeguridadMapper.toDTOList(scores);
    }

    @Override
    @Transactional(readOnly = true)
    public ScoreSeguridadDTO obtenerUltimoScorePorPersona(UUID personaId) {
        log.info("Obteniendo último score para persona ID: {}", personaId);

        ScoreSeguridad ultimoScore = scoreSeguridadRepository.findUltimoScoreByPersonaId(personaId)
                .orElseThrow(() -> new PersonaNotFoundException("No se encontró ningún score para la persona ID: " + personaId));

        return scoreSeguridadMapper.toDTO(ultimoScore);
    }

    @Override
    public ScoreSeguridadDTO actualizarScoreSeguridad(UUID scoreId, ScoreSeguridadDTO scoreSeguridadDTO) {
        log.info("Actualizando score de seguridad con ID: {}", scoreId);

        ScoreSeguridad scoreExistente = scoreSeguridadRepository.findById(scoreId)
                .orElseThrow(() -> new PersonaNotFoundException("Score de seguridad no encontrado con ID: " + scoreId));

        scoreSeguridadMapper.updateEntityFromDTO(scoreSeguridadDTO, scoreExistente);
        ScoreSeguridad scoreActualizado = scoreSeguridadRepository.save(scoreExistente);

        log.info("Score de seguridad actualizado exitosamente con ID: {}", scoreId);
        return scoreSeguridadMapper.toDTO(scoreActualizado);
    }

    @Override
    public void eliminarScoreSeguridad(UUID scoreId) {
        log.info("Eliminando score de seguridad con ID: {}", scoreId);

        if (!scoreSeguridadRepository.existsById(scoreId)) {
            throw new PersonaNotFoundException("Score de seguridad no encontrado con ID: " + scoreId);
        }

        scoreSeguridadRepository.deleteById(scoreId);
        log.info("Score de seguridad eliminado exitosamente con ID: {}", scoreId);
    }

    @Override
    @Transactional(readOnly = true)
    public Double calcularPromedioScorePersona(UUID personaId) {
        log.info("Calculando promedio de scores para persona ID: {}", personaId);

        List<ScoreSeguridad> scores = scoreSeguridadRepository.findByPersonaIdOrderByFechaEvaluacionDesc(personaId);

        if (scores.isEmpty()) {
            log.warn("No se encontraron scores para la persona ID: {}", personaId);
            return null;
        }

        OptionalDouble promedio = scores.stream()
                .filter(score -> score.getPuntuacion() != null)
                .mapToInt(ScoreSeguridad::getPuntuacion)
                .average();

        if (promedio.isPresent()) {
            double promedioCalculado = Math.round(promedio.getAsDouble() * 100.0) / 100.0; // Redondear a 2 decimales
            log.info("Promedio calculado para persona ID {}: {}", personaId, promedioCalculado);
            return promedioCalculado;
        }

        log.warn("No se pudo calcular el promedio para persona ID: {}", personaId);
        return null;
    }
}
