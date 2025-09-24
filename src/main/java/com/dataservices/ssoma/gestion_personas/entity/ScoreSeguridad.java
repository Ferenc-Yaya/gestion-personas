package com.dataservices.ssoma.gestion_personas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "SCORE_SEGURIDAD")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreSeguridad {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "score_id")
    private UUID scoreId;

    @Column(name = "persona_id", nullable = false)
    private UUID personaId;

    @Column(name = "puntuacion")
    @Min(value = 0, message = "La puntuación mínima es 0")
    @Max(value = 100, message = "La puntuación máxima es 100")
    private Integer puntuacion;

    @Column(name = "fecha_evaluacion")
    @NotNull(message = "La fecha de evaluación es obligatoria")
    private LocalDate fechaEvaluacion;

    @Column(name = "motivo_cambio", columnDefinition = "TEXT")
    private String motivoCambio;

    // Relación
    @ManyToOne
    @JoinColumn(name = "persona_id", insertable = false, updatable = false)
    private Persona persona;
}
