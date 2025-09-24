package com.dataservices.ssoma.gestion_personas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "PERSONAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Persona {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "persona_id")
    private UUID personaId;

    @Column(name = "empresa_id", nullable = false)
    @NotNull(message = "La empresa es obligatoria")
    private UUID empresaId;

    @Column(name = "tipo_persona", nullable = false, length = 50)
    @NotBlank(message = "El tipo de persona es obligatorio")
    @Pattern(regexp = "^(Empleado|Contratista)$", message = "Tipo debe ser Empleado o Contratista")
    private String tipoPersona;

    @Column(name = "nombre_completo", nullable = false)
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
    private String nombreCompleto;

    @Column(name = "dni", nullable = false, length = 20)
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^[0-9]{8}$", message = "DNI debe tener 8 dígitos")
    private String dni;

    @Column(name = "fecha_nacimiento")
    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private LocalDate fechaNacimiento;

    @Column(name = "genero", length = 20)
    private String genero;

    @Column(name = "telefono", length = 20)
    @Pattern(regexp = "^[+]?[0-9]{9,15}$", message = "Formato de teléfono inválido")
    private String telefono;

    @Column(name = "correo")
    @Email(message = "Formato de email inválido")
    @Size(max = 255, message = "El email no puede exceder 255 caracteres")
    private String correo;

    // Relaciones
    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SaludPersona saludPersona;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Certificacion> certificaciones;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScoreSeguridad> scoresSeguridad;
}
