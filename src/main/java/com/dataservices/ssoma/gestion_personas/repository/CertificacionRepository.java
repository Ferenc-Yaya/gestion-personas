package com.dataservices.ssoma.gestion_personas.repository;

import com.dataservices.ssoma.gestion_personas.entity.Certificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface CertificacionRepository extends JpaRepository<Certificacion, UUID> {

    List<Certificacion> findByPersonaId(UUID personaId);

    @Query("SELECT c FROM Certificacion c WHERE c.fechaVencimiento BETWEEN :inicio AND :fin")
    List<Certificacion> findByFechaVencimientoBetween(@Param("inicio") LocalDate inicio,
                                                      @Param("fin") LocalDate fin);

    @Query("SELECT c FROM Certificacion c WHERE c.fechaVencimiento <= :fecha")
    List<Certificacion> findVencidas(@Param("fecha") LocalDate fecha);

    void deleteByPersonaId(UUID personaId);
}
