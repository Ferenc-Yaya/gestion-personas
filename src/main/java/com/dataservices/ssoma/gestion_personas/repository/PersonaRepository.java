package com.dataservices.ssoma.gestion_personas.repository;

import com.dataservices.ssoma.gestion_personas.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, UUID> {

    Optional<Persona> findByDni(String dni);

    List<Persona> findByEmpresaId(UUID empresaId);

    List<Persona> findByTipoPersona(String tipoPersona);

    @Query("SELECT p FROM Persona p WHERE p.nombreCompleto LIKE %:nombre%")
    List<Persona> findByNombreCompletoContaining(@Param("nombre") String nombre);

    @Query("SELECT p FROM Persona p WHERE p.empresaId = :empresaId AND p.tipoPersona = :tipo")
    List<Persona> findByEmpresaIdAndTipoPersona(@Param("empresaId") UUID empresaId,
                                                @Param("tipo") String tipoPersona);

    boolean existsByDni(String dni);

    boolean existsByCorreo(String correo);
}
