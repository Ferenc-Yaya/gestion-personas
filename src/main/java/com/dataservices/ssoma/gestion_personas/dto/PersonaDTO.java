package com.dataservices.ssoma.gestion_personas.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonaDTO {
    private UUID personaId;

    @NotNull(message = "La empresa es obligatoria")
    private UUID empresaId;

    @NotBlank(message = "El tipo de persona es obligatorio")
    @Pattern(regexp = "^(Empleado|Contratista)$", message = "Tipo debe ser Empleado o Contratista")
    private String tipoPersona;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
    private String nombreCompleto;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^[0-9]{8}$", message = "DNI debe tener 8 dígitos")
    private String dni;

    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private LocalDate fechaNacimiento;

    private String genero;

    @Pattern(regexp = "^[+]?[0-9]{9,15}$", message = "Formato de teléfono inválido")
    private String telefono;

    @Email(message = "Formato de email inválido")
    @Size(max = 255, message = "El email no puede exceder 255 caracteres")
    private String correo;

    private SaludPersonaDTO saludPersona;
    private List<CertificacionDTO> certificaciones;
    private List<ScoreSeguridadDTO> scoresSeguridad;
}

