package com.dataservices.ssoma.gestion_personas.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificacionDTO {
    private UUID certificacionId;
    private UUID personaId;

    @NotBlank(message = "El nombre de la certificación es obligatorio")
    @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
    private String nombreCertificacion;

    @PastOrPresent(message = "La fecha de emisión no puede ser futura")
    private LocalDate fechaEmision;

    @Future(message = "La fecha de vencimiento debe ser futura")
    private LocalDate fechaVencimiento;
}
