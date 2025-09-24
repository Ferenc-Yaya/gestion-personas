package com.dataservices.ssoma.gestion_personas.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaludPersonaDTO {
    private UUID saludId;
    private UUID personaId;
    private String grupoSanguineo;
    private String alergias;
    private String historialMedico;
}
