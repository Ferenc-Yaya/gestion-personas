package com.dataservices.ssoma.gestion_personas.service.impl;

import com.dataservices.ssoma.gestion_personas.dto.PersonaDTO;
import com.dataservices.ssoma.gestion_personas.entity.Persona;
import com.dataservices.ssoma.gestion_personas.exception.DuplicatedResourceException;
import com.dataservices.ssoma.gestion_personas.exception.PersonaNotFoundException;
import com.dataservices.ssoma.gestion_personas.mapper.PersonaMapper;
import com.dataservices.ssoma.gestion_personas.repository.PersonaRepository;
import com.dataservices.ssoma.gestion_personas.service.PersonaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository personaRepository;
    private final PersonaMapper personaMapper;

    @Override
    public PersonaDTO crearPersona(PersonaDTO personaDTO) {
        log.info("Creando nueva persona con DNI: {}", personaDTO.getDni());

        // Validar que no exista el DNI
        if (personaRepository.existsByDni(personaDTO.getDni())) {
            throw new DuplicatedResourceException("Ya existe una persona con el DNI: " + personaDTO.getDni());
        }

        // Validar que no exista el correo (si se proporciona)
        if (personaDTO.getCorreo() != null && personaRepository.existsByCorreo(personaDTO.getCorreo())) {
            throw new DuplicatedResourceException("Ya existe una persona con el correo: " + personaDTO.getCorreo());
        }

        Persona persona = personaMapper.toEntity(personaDTO);
        Persona personaGuardada = personaRepository.save(persona);

        log.info("Persona creada exitosamente con ID: {}", personaGuardada.getPersonaId());
        return personaMapper.toDTO(personaGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaDTO obtenerPersonaPorId(UUID personaId) {
        log.info("Buscando persona con ID: {}", personaId);

        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new PersonaNotFoundException("Persona no encontrada con ID: " + personaId));

        return personaMapper.toDTO(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonaDTO obtenerPersonaPorDni(String dni) {
        log.info("Buscando persona con DNI: {}", dni);

        Persona persona = personaRepository.findByDni(dni)
                .orElseThrow(() -> new PersonaNotFoundException("Persona no encontrada con DNI: " + dni));

        return personaMapper.toDTO(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaDTO> obtenerPersonasPorEmpresa(UUID empresaId) {
        log.info("Buscando personas de la empresa: {}", empresaId);

        List<Persona> personas = personaRepository.findByEmpresaId(empresaId);
        return personaMapper.toDTOList(personas);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaDTO> obtenerPersonasPorTipo(String tipoPersona) {
        log.info("Buscando personas de tipo: {}", tipoPersona);

        List<Persona> personas = personaRepository.findByTipoPersona(tipoPersona);
        return personaMapper.toDTOList(personas);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PersonaDTO> obtenerTodasLasPersonas(Pageable pageable) {
        log.info("Obteniendo todas las personas - Página: {}, Tamaño: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Persona> personas = personaRepository.findAll(pageable);
        return personas.map(personaMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaDTO> buscarPersonasPorNombre(String nombre) {
        log.info("Buscando personas con nombre que contenga: {}", nombre);

        List<Persona> personas = personaRepository.findByNombreCompletoContaining(nombre);
        return personaMapper.toDTOList(personas);
    }

    @Override
    public PersonaDTO actualizarPersona(UUID personaId, PersonaDTO personaDTO) {
        log.info("Actualizando persona con ID: {}", personaId);

        Persona personaExistente = personaRepository.findById(personaId)
                .orElseThrow(() -> new PersonaNotFoundException("Persona no encontrada con ID: " + personaId));

        // Validar DNI si cambió
        if (!personaExistente.getDni().equals(personaDTO.getDni()) &&
                personaRepository.existsByDni(personaDTO.getDni())) {
            throw new DuplicatedResourceException("Ya existe una persona con el DNI: " + personaDTO.getDni());
        }

        // Validar correo si cambió
        if (personaDTO.getCorreo() != null &&
                !personaDTO.getCorreo().equals(personaExistente.getCorreo()) &&
                personaRepository.existsByCorreo(personaDTO.getCorreo())) {
            throw new DuplicatedResourceException("Ya existe una persona con el correo: " + personaDTO.getCorreo());
        }

        // Actualizar campos
        personaMapper.updateEntityFromDTO(personaDTO, personaExistente);
        Persona personaActualizada = personaRepository.save(personaExistente);

        log.info("Persona actualizada exitosamente con ID: {}", personaId);
        return personaMapper.toDTO(personaActualizada);
    }

    @Override
    public void eliminarPersona(UUID personaId) {
        log.info("Eliminando persona con ID: {}", personaId);

        if (!personaRepository.existsById(personaId)) {
            throw new PersonaNotFoundException("Persona no encontrada con ID: " + personaId);
        }

        personaRepository.deleteById(personaId);
        log.info("Persona eliminada exitosamente con ID: {}", personaId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePersonaPorDni(String dni) {
        return personaRepository.existsByDni(dni);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePersonaPorCorreo(String correo) {
        return personaRepository.existsByCorreo(correo);
    }
}
