package com.dataservices.ssoma.gestion_personas.service;

import com.dataservices.ssoma.gestion_personas.dto.SaludPersonaDTO;

import java.util.UUID;

public interface SaludPersonaService {

    SaludPersonaDTO crearSaludPersona(SaludPersonaDTO saludPersonaDTO);

    SaludPersonaDTO obtenerSaludPersonaPorId(UUID saludId);

    SaludPersonaDTO obtenerSaludPersonaPorPersonaId(UUID personaId);

    SaludPersonaDTO actualizarSaludPersona(UUID saludId, SaludPersonaDTO saludPersonaDTO);

    void eliminarSaludPersona(UUID saludId);
}
