package com.dataservices.ssoma.gestion_personas.controller;

import com.dataservices.ssoma.gestion_personas.dto.PersonaDTO;
import com.dataservices.ssoma.gestion_personas.dto.response.ApiResponse;
import com.dataservices.ssoma.gestion_personas.dto.response.PageResponse;
import com.dataservices.ssoma.gestion_personas.service.PersonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/personas")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PersonaController {

    private final PersonaService personaService;

    @PostMapping
    public ResponseEntity<ApiResponse<PersonaDTO>> crearPersona(@Valid @RequestBody PersonaDTO personaDTO) {
        log.info("Solicitud para crear persona con DNI: {}", personaDTO.getDni());

        PersonaDTO personaCreada = personaService.crearPersona(personaDTO);
        ApiResponse<PersonaDTO> response = ApiResponse.success(personaCreada, "Persona creada exitosamente");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{personaId}")
    public ResponseEntity<ApiResponse<PersonaDTO>> obtenerPersonaPorId(@PathVariable UUID personaId) {
        log.info("Solicitud para obtener persona con ID: {}", personaId);

        PersonaDTO persona = personaService.obtenerPersonaPorId(personaId);
        ApiResponse<PersonaDTO> response = ApiResponse.success(persona);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<ApiResponse<PersonaDTO>> obtenerPersonaPorDni(@PathVariable String dni) {
        log.info("Solicitud para obtener persona con DNI: {}", dni);

        PersonaDTO persona = personaService.obtenerPersonaPorDni(dni);
        ApiResponse<PersonaDTO> response = ApiResponse.success(persona);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PersonaDTO>>> obtenerTodasLasPersonas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombreCompleto") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        log.info("Solicitud para obtener todas las personas - Página: {}, Tamaño: {}", page, size);

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PersonaDTO> personas = personaService.obtenerTodasLasPersonas(pageable);

        PageResponse<PersonaDTO> pageResponse = PageResponse.from(personas);
        ApiResponse<PageResponse<PersonaDTO>> response = ApiResponse.success(pageResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<ApiResponse<List<PersonaDTO>>> obtenerPersonasPorEmpresa(@PathVariable UUID empresaId) {
        log.info("Solicitud para obtener personas de la empresa: {}", empresaId);

        List<PersonaDTO> personas = personaService.obtenerPersonasPorEmpresa(empresaId);
        ApiResponse<List<PersonaDTO>> response = ApiResponse.success(personas);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/tipo/{tipoPersona}")
    public ResponseEntity<ApiResponse<List<PersonaDTO>>> obtenerPersonasPorTipo(@PathVariable String tipoPersona) {
        log.info("Solicitud para obtener personas de tipo: {}", tipoPersona);

        List<PersonaDTO> personas = personaService.obtenerPersonasPorTipo(tipoPersona);
        ApiResponse<List<PersonaDTO>> response = ApiResponse.success(personas);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<PersonaDTO>>> buscarPersonasPorNombre(@RequestParam String nombre) {
        log.info("Solicitud para buscar personas con nombre: {}", nombre);

        List<PersonaDTO> personas = personaService.buscarPersonasPorNombre(nombre);
        ApiResponse<List<PersonaDTO>> response = ApiResponse.success(personas);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{personaId}")
    public ResponseEntity<ApiResponse<PersonaDTO>> actualizarPersona(
            @PathVariable UUID personaId,
            @Valid @RequestBody PersonaDTO personaDTO) {

        log.info("Solicitud para actualizar persona con ID: {}", personaId);

        PersonaDTO personaActualizada = personaService.actualizarPersona(personaId, personaDTO);
        ApiResponse<PersonaDTO> response = ApiResponse.success(personaActualizada, "Persona actualizada exitosamente");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{personaId}")
    public ResponseEntity<ApiResponse<Void>> eliminarPersona(@PathVariable UUID personaId) {
        log.info("Solicitud para eliminar persona con ID: {}", personaId);

        personaService.eliminarPersona(personaId);
        ApiResponse<Void> response = ApiResponse.success(null, "Persona eliminada exitosamente");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/existe/dni/{dni}")
    public ResponseEntity<ApiResponse<Boolean>> existePersonaPorDni(@PathVariable String dni) {
        boolean existe = personaService.existePersonaPorDni(dni);
        ApiResponse<Boolean> response = ApiResponse.success(existe);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/existe/correo/{correo}")
    public ResponseEntity<ApiResponse<Boolean>> existePersonaPorCorreo(@PathVariable String correo) {
        boolean existe = personaService.existePersonaPorCorreo(correo);
        ApiResponse<Boolean> response = ApiResponse.success(existe);

        return ResponseEntity.ok(response);
    }
}
