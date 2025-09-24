package com.dataservices.ssoma.gestion_personas.mapper;

import com.dataservices.ssoma.gestion_personas.dto.SaludPersonaDTO;
import com.dataservices.ssoma.gestion_personas.entity.SaludPersona;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SaludPersonaMapper {

    SaludPersonaDTO toDTO(SaludPersona saludPersona);

    SaludPersona toEntity(SaludPersonaDTO saludPersonaDTO);

    List<SaludPersonaDTO> toDTOList(List<SaludPersona> saludPersonas);

    List<SaludPersona> toEntityList(List<SaludPersonaDTO> saludPersonasDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "saludId", ignore = true)
    void updateEntityFromDTO(SaludPersonaDTO saludPersonaDTO, @MappingTarget SaludPersona saludPersona);
}
