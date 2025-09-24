package com.dataservices.ssoma.gestion_personas.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "SALUD_PERSONAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaludPersona {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "salud_id")
    private UUID saludId;

    @Column(name = "persona_id", nullable = false)
    private UUID personaId;

    @Column(name = "grupo_sanguineo", length = 10)
    private String grupoSanguineo;

    @Column(name = "alergias", columnDefinition = "TEXT")
    private String alergias;

    @Column(name = "historial_medico", columnDefinition = "TEXT")
    private String historialMedico;

    // Relación
    @OneToOne
    @JoinColumn(name = "persona_id", insertable = false, updatable = false)
    private Persona persona;
}
