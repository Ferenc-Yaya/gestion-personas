package com.dataservices.ssoma.gestion_personas.mapper;

import com.dataservices.ssoma.gestion_personas.dto.ScoreSeguridadDTO;
import com.dataservices.ssoma.gestion_personas.entity.ScoreSeguridad;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScoreSeguridadMapper {

    ScoreSeguridadDTO toDTO(ScoreSeguridad scoreSeguridad);

    ScoreSeguridad toEntity(ScoreSeguridadDTO scoreSeguridadDTO);

    List<ScoreSeguridadDTO> toDTOList(List<ScoreSeguridad> scoresSeguridad);

    List<ScoreSeguridad> toEntityList(List<ScoreSeguridadDTO> scoresSeguridadDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "scoreId", ignore = true)
    void updateEntityFromDTO(ScoreSeguridadDTO scoreSeguridadDTO, @MappingTarget ScoreSeguridad scoreSeguridad);
}
