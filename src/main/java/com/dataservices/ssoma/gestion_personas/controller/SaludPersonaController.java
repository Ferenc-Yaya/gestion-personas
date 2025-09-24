package com.dataservices.ssoma.gestion_personas.controller;

import com.dataservices.ssoma.gestion_personas.dto.SaludPersonaDTO;
import com.dataservices.ssoma.gestion_personas.dto.response.ApiResponse;
import com.dataservices.ssoma.gestion_personas.service.SaludPersonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/salud-personas")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class SaludPersonaController {

    private final SaludPersonaService saludPersonaService;

    @PostMapping
    public ResponseEntity<ApiResponse<SaludPersonaDTO>> crearSaludPersona(@Valid @RequestBody SaludPersonaDTO saludPersonaDTO) {
        log.info("Solicitud para crear información de salud para persona ID: {}", saludPersonaDTO.getPersonaId());

        SaludPersonaDTO saludPersonaCreada = saludPersonaService.crearSaludPersona(saludPersonaDTO);
        ApiResponse<SaludPersonaDTO> response = ApiResponse.success(saludPersonaCreada, "Información de salud creada exitosamente");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{saludId}")
    public ResponseEntity<ApiResponse<SaludPersonaDTO>> obtenerSaludPersonaPorId(@PathVariable UUID saludId) {
        log.info("Solicitud para obtener información de salud con ID: {}", saludId);

        SaludPersonaDTO saludPersona = saludPersonaService.obtenerSaludPersonaPorId(saludId);
        ApiResponse<SaludPersonaDTO> response = ApiResponse.success(saludPersona);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/persona/{personaId}")
    public ResponseEntity<ApiResponse<SaludPersonaDTO>> obtenerSaludPersonaPorPersonaId(@PathVariable UUID personaId) {
        log.info("Solicitud para obtener información de salud para persona ID: {}", personaId);

        SaludPersonaDTO saludPersona = saludPersonaService.obtenerSaludPersonaPorPersonaId(personaId);
        ApiResponse<SaludPersonaDTO> response = ApiResponse.success(saludPersona);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{saludId}")
    public ResponseEntity<ApiResponse<SaludPersonaDTO>> actualizarSaludPersona(
            @PathVariable UUID saludId,
            @Valid @RequestBody SaludPersonaDTO saludPersonaDTO) {

        log.info("Solicitud para actualizar información de salud con ID: {}", saludId);

        SaludPersonaDTO saludPersonaActualizada = saludPersonaService.actualizarSaludPersona(saludId, saludPersonaDTO);
        ApiResponse<SaludPersonaDTO> response = ApiResponse.success(saludPersonaActualizada, "Información de salud actualizada exitosamente");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{saludId}")
    public ResponseEntity<ApiResponse<Void>> eliminarSaludPersona(@PathVariable UUID saludId) {
        log.info("Solicitud para eliminar información de salud con ID: {}", saludId);

        saludPersonaService.eliminarSaludPersona(saludId);
        ApiResponse<Void> response = ApiResponse.success(null, "Información de salud eliminada exitosamente");

        return ResponseEntity.ok(response);
    }
}
