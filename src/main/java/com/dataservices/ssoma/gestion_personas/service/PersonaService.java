package com.dataservices.ssoma.gestion_personas.service;

import com.dataservices.ssoma.gestion_personas.dto.PersonaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PersonaService {

    PersonaDTO crearPersona(PersonaDTO personaDTO);

    PersonaDTO obtenerPersonaPorId(UUID personaId);

    PersonaDTO obtenerPersonaPorDni(String dni);

    List<PersonaDTO> obtenerPersonasPorEmpresa(UUID empresaId);

    List<PersonaDTO> obtenerPersonasPorTipo(String tipoPersona);

    Page<PersonaDTO> obtenerTodasLasPersonas(Pageable pageable);

    List<PersonaDTO> buscarPersonasPorNombre(String nombre);

    PersonaDTO actualizarPersona(UUID personaId, PersonaDTO personaDTO);

    void eliminarPersona(UUID personaId);

    boolean existePersonaPorDni(String dni);

    boolean existePersonaPorCorreo(String correo);
}
