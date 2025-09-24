package com.dataservices.ssoma.gestion_personas.controller;

import com.dataservices.ssoma.gestion_personas.dto.CertificacionDTO;
import com.dataservices.ssoma.gestion_personas.dto.response.ApiResponse;
import com.dataservices.ssoma.gestion_personas.service.CertificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/certificaciones")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CertificacionController {

    private final CertificacionService certificacionService;

    @PostMapping
    public ResponseEntity<ApiResponse<CertificacionDTO>> crearCertificacion(@Valid @RequestBody CertificacionDTO certificacionDTO) {
        log.info("Solicitud para crear certificación: {} para persona ID: {}",
                certificacionDTO.getNombreCertificacion(), certificacionDTO.getPersonaId());

        CertificacionDTO certificacionCreada = certificacionService.crearCertificacion(certificacionDTO);
        ApiResponse<CertificacionDTO> response = ApiResponse.success(certificacionCreada, "Certificación creada exitosamente");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{certificacionId}")
    public ResponseEntity<ApiResponse<CertificacionDTO>> obtenerCertificacionPorId(@PathVariable UUID certificacionId) {
        log.info("Solicitud para obtener certificación con ID: {}", certificacionId);

        CertificacionDTO certificacion = certificacionService.obtenerCertificacionPorId(certificacionId);
        ApiResponse<CertificacionDTO> response = ApiResponse.success(certificacion);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/persona/{personaId}")
    public ResponseEntity<ApiResponse<List<CertificacionDTO>>> obtenerCertificacionesPorPersona(@PathVariable UUID personaId) {
        log.info("Solicitud para obtener certificaciones de persona ID: {}", personaId);

        List<CertificacionDTO> certificaciones = certificacionService.obtenerCertificacionesPorPersona(personaId);
        ApiResponse<List<CertificacionDTO>> response = ApiResponse.success(certificaciones);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/proximas-vencer")
    public ResponseEntity<ApiResponse<List<CertificacionDTO>>> obtenerCertificacionesProximasAVencer(
            @RequestParam(defaultValue = "30") int diasAntesVencimiento) {

        log.info("Solicitud para obtener certificaciones próximas a vencer en {} días", diasAntesVencimiento);

        List<CertificacionDTO> certificaciones = certificacionService.obtenerCertificacionesProximasAVencer(diasAntesVencimiento);
        ApiResponse<List<CertificacionDTO>> response = ApiResponse.success(certificaciones);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/vencidas")
    public ResponseEntity<ApiResponse<List<CertificacionDTO>>> obtenerCertificacionesVencidas() {
        log.info("Solicitud para obtener certificaciones vencidas");

        List<CertificacionDTO> certificaciones = certificacionService.obtenerCertificacionesVencidas();
        ApiResponse<List<CertificacionDTO>> response = ApiResponse.success(certificaciones);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{certificacionId}")
    public ResponseEntity<ApiResponse<CertificacionDTO>> actualizarCertificacion(
            @PathVariable UUID certificacionId,
            @Valid @RequestBody CertificacionDTO certificacionDTO) {

        log.info("Solicitud para actualizar certificación con ID: {}", certificacionId);

        CertificacionDTO certificacionActualizada = certificacionService.actualizarCertificacion(certificacionId, certificacionDTO);
        ApiResponse<CertificacionDTO> response = ApiResponse.success(certificacionActualizada, "Certificación actualizada exitosamente");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{certificacionId}")
    public ResponseEntity<ApiResponse<Void>> eliminarCertificacion(@PathVariable UUID certificacionId) {
        log.info("Solicitud para eliminar certificación con ID: {}", certificacionId);

        certificacionService.eliminarCertificacion(certificacionId);
        ApiResponse<Void> response = ApiResponse.success(null, "Certificación eliminada exitosamente");

        return ResponseEntity.ok(response);
    }
}

