package com.dataservices.ssoma.gestion_personas.service.impl;

import com.dataservices.ssoma.gestion_personas.dto.CertificacionDTO;
import com.dataservices.ssoma.gestion_personas.entity.Certificacion;
import com.dataservices.ssoma.gestion_personas.exception.PersonaNotFoundException;
import com.dataservices.ssoma.gestion_personas.mapper.CertificacionMapper;
import com.dataservices.ssoma.gestion_personas.repository.CertificacionRepository;
import com.dataservices.ssoma.gestion_personas.service.CertificacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CertificacionServiceImpl implements CertificacionService {

    private final CertificacionRepository certificacionRepository;
    private final CertificacionMapper certificacionMapper;

    @Override
    public CertificacionDTO crearCertificacion(CertificacionDTO certificacionDTO) {
        log.info("Creando nueva certificación: {} para persona ID: {}",
                certificacionDTO.getNombreCertificacion(), certificacionDTO.getPersonaId());

        Certificacion certificacion = certificacionMapper.toEntity(certificacionDTO);
        Certificacion certificacionGuardada = certificacionRepository.save(certificacion);

        log.info("Certificación creada exitosamente con ID: {}", certificacionGuardada.getCertificacionId());
        return certificacionMapper.toDTO(certificacionGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public CertificacionDTO obtenerCertificacionPorId(UUID certificacionId) {
        log.info("Buscando certificación con ID: {}", certificacionId);

        Certificacion certificacion = certificacionRepository.findById(certificacionId)
                .orElseThrow(() -> new PersonaNotFoundException("Certificación no encontrada con ID: " + certificacionId));

        return certificacionMapper.toDTO(certificacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificacionDTO> obtenerCertificacionesPorPersona(UUID personaId) {
        log.info("Buscando certificaciones para persona ID: {}", personaId);

        List<Certificacion> certificaciones = certificacionRepository.findByPersonaId(personaId);
        return certificacionMapper.toDTOList(certificaciones);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificacionDTO> obtenerCertificacionesProximasAVencer(int diasAntesVencimiento) {
        log.info("Buscando certificaciones próximas a vencer en {} días", diasAntesVencimiento);

        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaLimite = fechaActual.plusDays(diasAntesVencimiento);

        List<Certificacion> certificaciones = certificacionRepository
                .findByFechaVencimientoBetween(fechaActual, fechaLimite);

        log.info("Encontradas {} certificaciones próximas a vencer", certificaciones.size());
        return certificacionMapper.toDTOList(certificaciones);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificacionDTO> obtenerCertificacionesVencidas() {
        log.info("Buscando certificaciones vencidas");

        LocalDate fechaActual = LocalDate.now();
        List<Certificacion> certificaciones = certificacionRepository.findVencidas(fechaActual);

        log.info("Encontradas {} certificaciones vencidas", certificaciones.size());
        return certificacionMapper.toDTOList(certificaciones);
    }

    @Override
    public CertificacionDTO actualizarCertificacion(UUID certificacionId, CertificacionDTO certificacionDTO) {
        log.info("Actualizando certificación con ID: {}", certificacionId);

        Certificacion certificacionExistente = certificacionRepository.findById(certificacionId)
                .orElseThrow(() -> new PersonaNotFoundException("Certificación no encontrada con ID: " + certificacionId));

        certificacionMapper.updateEntityFromDTO(certificacionDTO, certificacionExistente);
        Certificacion certificacionActualizada = certificacionRepository.save(certificacionExistente);

        log.info("Certificación actualizada exitosamente con ID: {}", certificacionId);
        return certificacionMapper.toDTO(certificacionActualizada);
    }

    @Override
    public void eliminarCertificacion(UUID certificacionId) {
        log.info("Eliminando certificación con ID: {}", certificacionId);

        if (!certificacionRepository.existsById(certificacionId)) {
            throw new PersonaNotFoundException("Certificación no encontrada con ID: " + certificacionId);
        }

        certificacionRepository.deleteById(certificacionId);
        log.info("Certificación eliminada exitosamente con ID: {}", certificacionId);
    }
}
