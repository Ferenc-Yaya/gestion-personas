package com.dataservices.ssoma.gestion_personas.mapper;

import com.dataservices.ssoma.gestion_personas.dto.PersonaDTO;
import com.dataservices.ssoma.gestion_personas.entity.Persona;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonaMapper {

    @Mapping(target = "saludPersona", ignore = true)
    @Mapping(target = "certificaciones", ignore = true)
    @Mapping(target = "scoresSeguridad", ignore = true)
    PersonaDTO toDTO(Persona persona);

    @Mapping(target = "saludPersona", ignore = true)
    @Mapping(target = "certificaciones", ignore = true)
    @Mapping(target = "scoresSeguridad", ignore = true)
    Persona toEntity(PersonaDTO personaDTO);

    List<PersonaDTO> toDTOList(List<Persona> personas);

    List<Persona> toEntityList(List<PersonaDTO> personasDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "personaId", ignore = true)
    @Mapping(target = "saludPersona", ignore = true)
    @Mapping(target = "certificaciones", ignore = true)
    @Mapping(target = "scoresSeguridad", ignore = true)
    void updateEntityFromDTO(PersonaDTO personaDTO, @MappingTarget Persona persona);
}
