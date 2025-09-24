package com.dataservices.ssoma.gestion_personas.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreSeguridadDTO {
    private UUID scoreId;
    private UUID personaId;

    @Min(value = 0, message = "La puntuación mínima es 0")
    @Max(value = 100, message = "La puntuación máxima es 100")
    private Integer puntuacion;

    @NotNull(message = "La fecha de evaluación es obligatoria")
    private LocalDate fechaEvaluacion;

    private String motivoCambio;
}
