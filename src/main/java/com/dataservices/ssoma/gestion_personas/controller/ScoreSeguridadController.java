package com.dataservices.ssoma.gestion_personas.controller;

import com.dataservices.ssoma.gestion_personas.dto.ScoreSeguridadDTO;
import com.dataservices.ssoma.gestion_personas.dto.response.ApiResponse;
import com.dataservices.ssoma.gestion_personas.service.ScoreSeguridadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/scores-seguridad")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ScoreSeguridadController {

    private final ScoreSeguridadService scoreSeguridadService;

    @PostMapping
    public ResponseEntity<ApiResponse<ScoreSeguridadDTO>> crearScoreSeguridad(@Valid @RequestBody ScoreSeguridadDTO scoreSeguridadDTO) {
        log.info("Solicitud para crear score de seguridad para persona ID: {} con puntuación: {}",
                scoreSeguridadDTO.getPersonaId(), scoreSeguridadDTO.getPuntuacion());

        ScoreSeguridadDTO scoreCreado = scoreSeguridadService.crearScoreSeguridad(scoreSeguridadDTO);
        ApiResponse<ScoreSeguridadDTO> response = ApiResponse.success(scoreCreado, "Score de seguridad creado exitosamente");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{scoreId}")
    public ResponseEntity<ApiResponse<ScoreSeguridadDTO>> obtenerScoreSeguridadPorId(@PathVariable UUID scoreId) {
        log.info("Solicitud para obtener score de seguridad con ID: {}", scoreId);

        ScoreSeguridadDTO scoreSeguridad = scoreSeguridadService.obtenerScoreSeguridadPorId(scoreId);
        ApiResponse<ScoreSeguridadDTO> response = ApiResponse.success(scoreSeguridad);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/persona/{personaId}/historial")
    public ResponseEntity<ApiResponse<List<ScoreSeguridadDTO>>> obtenerHistorialScoresPorPersona(@PathVariable UUID personaId) {
        log.info("Solicitud para obtener historial de scores de persona ID: {}", personaId);

        List<ScoreSeguridadDTO> historialScores = scoreSeguridadService.obtenerHistorialScoresPorPersona(personaId);
        ApiResponse<List<ScoreSeguridadDTO>> response = ApiResponse.success(historialScores);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/persona/{personaId}/ultimo")
    public ResponseEntity<ApiResponse<ScoreSeguridadDTO>> obtenerUltimoScorePorPersona(@PathVariable UUID personaId) {
        log.info("Solicitud para obtener último score de persona ID: {}", personaId);

        ScoreSeguridadDTO ultimoScore = scoreSeguridadService.obtenerUltimoScorePorPersona(personaId);
        ApiResponse<ScoreSeguridadDTO> response = ApiResponse.success(ultimoScore);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/persona/{personaId}/promedio")
    public ResponseEntity<ApiResponse<Double>> calcularPromedioScorePersona(@PathVariable UUID personaId) {
        log.info("Solicitud para calcular promedio de scores de persona ID: {}", personaId);

        Double promedio = scoreSeguridadService.calcularPromedioScorePersona(personaId);
        ApiResponse<Double> response = ApiResponse.success(promedio);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{scoreId}")
    public ResponseEntity<ApiResponse<ScoreSeguridadDTO>> actualizarScoreSeguridad(
            @PathVariable UUID scoreId,
            @Valid @RequestBody ScoreSeguridadDTO scoreSeguridadDTO) {

        log.info("Solicitud para actualizar score de seguridad con ID: {}", scoreId);

        ScoreSeguridadDTO scoreActualizado = scoreSeguridadService.actualizarScoreSeguridad(scoreId, scoreSeguridadDTO);
        ApiResponse<ScoreSeguridadDTO> response = ApiResponse.success(scoreActualizado, "Score de seguridad actualizado exitosamente");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{scoreId}")
    public ResponseEntity<ApiResponse<Void>> eliminarScoreSeguridad(@PathVariable UUID scoreId) {
        log.info("Solicitud para eliminar score de seguridad con ID: {}", scoreId);

        scoreSeguridadService.eliminarScoreSeguridad(scoreId);
        ApiResponse<Void> response = ApiResponse.success(null, "Score de seguridad eliminado exitosamente");

        return ResponseEntity.ok(response);
    }
}
