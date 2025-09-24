package com.dataservices.ssoma.gestion_personas.service;

import com.dataservices.ssoma.gestion_personas.dto.CertificacionDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CertificacionService {

    CertificacionDTO crearCertificacion(CertificacionDTO certificacionDTO);

    CertificacionDTO obtenerCertificacionPorId(UUID certificacionId);

    List<CertificacionDTO> obtenerCertificacionesPorPersona(UUID personaId);

    List<CertificacionDTO> obtenerCertificacionesProximasAVencer(int diasAntesVencimiento);

    List<CertificacionDTO> obtenerCertificacionesVencidas();

    CertificacionDTO actualizarCertificacion(UUID certificacionId, CertificacionDTO certificacionDTO);

    void eliminarCertificacion(UUID certificacionId);
}
