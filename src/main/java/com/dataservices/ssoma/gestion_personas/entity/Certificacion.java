package com.dataservices.ssoma.gestion_personas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "CERTIFICACIONES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certificacion {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "certificacion_id")
    private UUID certificacionId;

    @Column(name = "persona_id", nullable = false)
    private UUID personaId;

    @Column(name = "nombre_certificacion", nullable = false)
    @NotBlank(message = "El nombre de la certificación es obligatorio")
    @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
    private String nombreCertificacion;

    @Column(name = "fecha_emision")
    @PastOrPresent(message = "La fecha de emisión no puede ser futura")
    private LocalDate fechaEmision;

    @Column(name = "fecha_vencimiento")
    @Future(message = "La fecha de vencimiento debe ser futura")
    private LocalDate fechaVencimiento;

    // Relación
    @ManyToOne
    @JoinColumn(name = "persona_id", insertable = false, updatable = false)
    private Persona persona;
}
