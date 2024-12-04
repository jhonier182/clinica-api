package com.jhonier.ws.Controller;

import com.jhonier.ws.Dto.PersonaDto;
import com.jhonier.ws.Service.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicio")
@Tag(name = "PersonaController", description = "Endpoints para la gestión de personas")
public class PersonaController {

    private final PersonaService personaService;

    @Autowired
    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @Operation(summary = "Saludo de prueba", description = "Devuelve un mensaje de saludo.")
    @GetMapping("hola")
    public String saludo() {
        return "Este es el saludo de clínica Web";
    }

    @Operation(summary = "Consultar persona por ID", description = "Obtiene una persona específica basada en su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaDto.class))),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Parámetro inválido", content = @Content)
    })
    @GetMapping("personas")
    public ResponseEntity<?> getPersona(@RequestParam(value = "id", required = false) String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El parámetro 'id' es obligatorio.");
        }
        PersonaDto persona = personaService.obtenerPersonaPorDocumento(documento);
        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada para el documento: " + documento);
        }
        return ResponseEntity.ok(persona);
    }

    @Operation(summary = "Consultar persona por ID con PathVariable", description = "Obtiene una persona específica por su ID utilizando un parámetro en la URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaDto.class))),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Parámetro inválido", content = @Content)
    })
    @GetMapping("personas/{id}")
    public ResponseEntity<?> getPersonaId(@PathVariable("id") String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El parámetro 'id' es obligatorio.");
        }
        PersonaDto persona = personaService.obtenerPersonaPorDocumento(documento);
        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada para el documento: " + documento);
        }
        return ResponseEntity.ok(persona);
    }

    @Operation(summary = "Consultar persona por ID y profesión", description = "Obtiene una persona específica basada en su ID y profesión.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaDto.class))),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada", content = @Content)
    })
    @GetMapping("profesion")
    public ResponseEntity<?> getPersonaIdProfesion(@RequestParam("id") String documento,
                                                   @RequestParam("profesion") String profesion) {
        PersonaDto persona = personaService.consultarPersonaIdProfesion(documento, profesion);
        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada para el documento: " + documento + " y profesión: " + profesion);
        }
        return ResponseEntity.ok(persona);
    }

    @Operation(summary = "Consultar persona por ID y profesión en PathVariable", description = "Obtiene una persona específica por ID y profesión a través de la URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaDto.class))),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada", content = @Content)
    })
    @GetMapping("personas/{id}/{profesion}")
    public ResponseEntity<?> getPersonaIdProfesion2(@PathVariable("id") String documento,
                                                    @PathVariable String profesion) {
        PersonaDto persona = personaService.consultarPersonaIdProfesion(documento, profesion);
        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada para el documento: " + documento + " y profesión: " + profesion);
        }
        return ResponseEntity.ok(persona);
    }

    @Operation(summary = "Listar todas las personas", description = "Obtiene una lista de todas las personas registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de personas obtenida",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaDto.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron personas", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("persona-lista")
    public ResponseEntity<?> getPersonas() {
        try {
            List<PersonaDto> personas = personaService.obtenerListaPersonas();
            if (personas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(personas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Guardar persona", description = "Registra una nueva persona en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona registrada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping("guardar")
    public ResponseEntity<PersonaDto> guardarPersona(@RequestBody PersonaDto persona) {
        PersonaDto miPersona = personaService.registrarPersona(persona);
        if (miPersona != null) {
            return ResponseEntity.ok(miPersona);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Validar login", description = "Verifica las credenciales del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaDto.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas", content = @Content)
    })
    @PostMapping("login")
    public ResponseEntity<?> validarUsuario(@RequestBody PersonaDto persona) {
        PersonaDto miPersona = personaService.consultarLogin(persona);
        if (miPersona != null) {
            return ResponseEntity.ok(miPersona);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas.");
        }
    }

    @Operation(summary = "Actualizar persona", description = "Actualiza los datos de una persona existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona actualizada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaDto.class))),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada", content = @Content)
    })
    @PutMapping("actualizar")
    public ResponseEntity<?> actualizarPersona(@RequestBody PersonaDto persona) {
        PersonaDto personaActualizada = personaService.actualizarPersona(persona);
        if (personaActualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró una persona con el documento especificado.");
        }
        return ResponseEntity.ok(personaActualizada);
    }

    @Operation(summary = "Eliminar persona", description = "Elimina una persona específica por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona eliminada exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Parámetro inválido", content = @Content)
    })
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarPersona(@PathVariable String id) {
        if (id == null || id.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El parámetro 'id' es obligatorio.");
        }
        boolean eliminado = personaService.eliminarPersona(id);
        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una persona con el documento especificado.");
        }
        return ResponseEntity.ok("Persona eliminada exitosamente.");
    }
}
