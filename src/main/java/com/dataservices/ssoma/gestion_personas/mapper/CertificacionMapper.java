package com.dataservices.ssoma.gestion_personas.mapper;

import com.dataservices.ssoma.gestion_personas.dto.CertificacionDTO;
import com.dataservices.ssoma.gestion_personas.entity.Certificacion;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CertificacionMapper {

    CertificacionDTO toDTO(Certificacion certificacion);

    Certificacion toEntity(CertificacionDTO certificacionDTO);

    List<CertificacionDTO> toDTOList(List<Certificacion> certificaciones);

    List<Certificacion> toEntityList(List<CertificacionDTO> certificacionesDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "certificacionId", ignore = true)
    void updateEntityFromDTO(CertificacionDTO certificacionDTO, @MappingTarget Certificacion certificacion);
}
