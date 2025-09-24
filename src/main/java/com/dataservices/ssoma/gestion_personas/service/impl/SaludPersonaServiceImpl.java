package com.dataservices.ssoma.gestion_personas.service.impl;

import com.dataservices.ssoma.gestion_personas.dto.SaludPersonaDTO;
import com.dataservices.ssoma.gestion_personas.entity.SaludPersona;
import com.dataservices.ssoma.gestion_personas.exception.PersonaNotFoundException;
import com.dataservices.ssoma.gestion_personas.mapper.SaludPersonaMapper;
import com.dataservices.ssoma.gestion_personas.repository.SaludPersonaRepository;
import com.dataservices.ssoma.gestion_personas.service.SaludPersonaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SaludPersonaServiceImpl implements SaludPersonaService {

    private final SaludPersonaRepository saludPersonaRepository;
    private final SaludPersonaMapper saludPersonaMapper;

    @Override
    public SaludPersonaDTO crearSaludPersona(SaludPersonaDTO saludPersonaDTO) {
        log.info("Creando información de salud para persona ID: {}", saludPersonaDTO.getPersonaId());

        SaludPersona saludPersona = saludPersonaMapper.toEntity(saludPersonaDTO);
        SaludPersona saludPersonaGuardada = saludPersonaRepository.save(saludPersona);

        log.info("Información de salud creada exitosamente con ID: {}", saludPersonaGuardada.getSaludId());
        return saludPersonaMapper.toDTO(saludPersonaGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public SaludPersonaDTO obtenerSaludPersonaPorId(UUID saludId) {
        log.info("Buscando información de salud con ID: {}", saludId);

        SaludPersona saludPersona = saludPersonaRepository.findById(saludId)
                .orElseThrow(() -> new PersonaNotFoundException("Información de salud no encontrada con ID: " + saludId));

        return saludPersonaMapper.toDTO(saludPersona);
    }

    @Override
    @Transactional(readOnly = true)
    public SaludPersonaDTO obtenerSaludPersonaPorPersonaId(UUID personaId) {
        log.info("Buscando información de salud para persona ID: {}", personaId);

        SaludPersona saludPersona = saludPersonaRepository.findByPersonaId(personaId)
                .orElseThrow(() -> new PersonaNotFoundException("Información de salud no encontrada para persona ID: " + personaId));

        return saludPersonaMapper.toDTO(saludPersona);
    }

    @Override
    public SaludPersonaDTO actualizarSaludPersona(UUID saludId, SaludPersonaDTO saludPersonaDTO) {
        log.info("Actualizando información de salud con ID: {}", saludId);

        SaludPersona saludPersonaExistente = saludPersonaRepository.findById(saludId)
                .orElseThrow(() -> new PersonaNotFoundException("Información de salud no encontrada con ID: " + saludId));

        saludPersonaMapper.updateEntityFromDTO(saludPersonaDTO, saludPersonaExistente);
        SaludPersona saludPersonaActualizada = saludPersonaRepository.save(saludPersonaExistente);

        log.info("Información de salud actualizada exitosamente con ID: {}", saludId);
        return saludPersonaMapper.toDTO(saludPersonaActualizada);
    }

    @Override
    public void eliminarSaludPersona(UUID saludId) {
        log.info("Eliminando información de salud con ID: {}", saludId);

        if (!saludPersonaRepository.existsById(saludId)) {
            throw new PersonaNotFoundException("Información de salud no encontrada con ID: " + saludId);
        }

        saludPersonaRepository.deleteById(saludId);
        log.info("Información de salud eliminada exitosamente con ID: {}", saludId);
    }
}
