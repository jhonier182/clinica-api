package com.jhonier.ws.Controller;

import com.jhonier.ws.Dto.MascotaDto;
import com.jhonier.ws.Service.MascotaService;
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
@RequestMapping("/mascotas")
@Tag(name = "MascotaController", description = "Endpoints para la gestión de mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    @Autowired
    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @Operation(summary = "Saludo de prueba", description = "Devuelve un mensaje de saludo.")
    @GetMapping("hola")
    public String saludo() {
        return "Este es el saludo de servicio de mascotas";
    }

    @Operation(summary = "Consultar mascota por ID", description = "Obtiene una mascota específica basada en su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascota encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MascotaDto.class))),
            @ApiResponse(responseCode = "404", description = "Mascota no encontrada", content = @Content)
    })
    @GetMapping("mascota/{id}")
    public ResponseEntity<?> getMascotaById(@PathVariable("id") int id) {
        MascotaDto mascota = mascotaService.consultarMascotaPorId(id);
        if (mascota == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mascota no encontrada para el id: " + id);
        }
        return ResponseEntity.ok(mascota);
    }

    @Operation(summary = "Consultar todas las mascotas", description = "Obtiene todas las mascotas registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de mascotas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MascotaDto.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron mascotas", content = @Content)
    })
    @GetMapping("mascota-lista")
    public ResponseEntity<?> getTodasLasMascotas() {
        List<MascotaDto> mascotas = mascotaService.obtenerTodasLasMascotas();
        if (mascotas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron mascotas.");
        }
        return ResponseEntity.ok(mascotas);
    }

    @Operation(summary = "Registrar nueva mascota", description = "Permite registrar una nueva mascota en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascota registrada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MascotaDto.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content)
    })
    @PostMapping("registrar")
    public ResponseEntity<MascotaDto> registrarMascota(@RequestBody MascotaDto mascotaDto) {
        MascotaDto nuevaMascota = mascotaService.registrarMascota(mascotaDto);
        if (nuevaMascota != null) {
            return ResponseEntity.ok(nuevaMascota);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Actualizar mascota", description = "Permite actualizar los datos de una mascota existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascota actualizada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MascotaDto.class))),
            @ApiResponse(responseCode = "404", description = "Mascota no encontrada", content = @Content)
    })
    @PutMapping("actualizar/{id}")
    public ResponseEntity<?> actualizarMascota(@PathVariable int id, @RequestBody MascotaDto mascotaDto) {
        mascotaDto.setId(id);  // Aseguramos que el id de la mascota sea el que se pasa en la URL
        MascotaDto mascotaActualizada = mascotaService.actualizarMascota(id, mascotaDto);
        if (mascotaActualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una mascota con el id especificado");
        }
        return ResponseEntity.ok(mascotaActualizada);
    }

    @Operation(summary = "Eliminar mascota", description = "Elimina una mascota del sistema por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascota eliminada exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Mascota no encontrada", content = @Content)
    })
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarMascota(@PathVariable int id) {
        try {
            mascotaService.eliminarMascota(id);
            return ResponseEntity.ok("Mascota eliminada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una mascota con el id especificado.");
        }
    }

    @Operation(summary = "Consultar mascotas por ID de persona", description = "Obtiene una lista de mascotas asociadas a una persona por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascotas encontradas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MascotaDto.class))),
            @ApiResponse(responseCode = "404", description = "Mascotas no encontradas", content = @Content)
    })
    @GetMapping("mascotas/persona/{personaId}")
    public ResponseEntity<?> obtenerMascotasPorPersonaId(@PathVariable int personaId) {
        List<MascotaDto> mascotas = mascotaService.obtenerMascotasPorPersonaId(personaId);
        if (mascotas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron mascotas para la persona con ID: " + personaId);
        }
        return ResponseEntity.ok(mascotas);
    }
}
