package cl.duoc.mantencion_service.controller;

import cl.duoc.mantencion_service.exception.MantencionNotExistException;
import cl.duoc.mantencion_service.model.Mantencion;
import cl.duoc.mantencion_service.service.MantencionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Mantenciones",
        description = "Operaciones disponibles para la gestión de mantenciones"
)
@RestController
@RequestMapping("/api/v1/mantenciones")
public class MantencionController {

    private final MantencionService mantencionService;

    public MantencionController(MantencionService mantencionService) {
        this.mantencionService = mantencionService;
    }

    @Operation(
            summary = "Listar mantenciones",
            description = "Obtiene el listado completo de mantenciones registradas."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Mantenciones listadas correctamente",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Mantencion.class))
            )
    )
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping
    public ResponseEntity<List<Mantencion>> obtenerMantenciones() {
        List<Mantencion> mantenciones = mantencionService.obtenerMantenciones();
        return ResponseEntity.ok(mantenciones); // Retorna 200 OK explícito
    }

    @Operation(
            summary = "Obtener mantención",
            description = "Obtiene una mantención mediante su ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Mantención encontrada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Mantencion.class))
    )
    @ApiResponse(responseCode = "404", description = "Mantención no encontrada")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping("/{id}")
    public ResponseEntity<Mantencion> obtenerMantencionPorId(
            @Parameter(description = "ID único de la mantención", example = "1") // <-- Exigencia Punto 5
            @PathVariable Long id
    ) {
        // Asumiendo que tu Service maneja el Optional o lanza la excepción directamente, limpiamos el controlador
        Mantencion mantencion = mantencionService.buscarMantencionPorId(id);
        if (mantencion == null) {
            throw new MantencionNotExistException("Mantención no encontrada");
        }
        return ResponseEntity.ok(mantencion); // Retorna 200 OK si existe
    }

    @Operation(
            summary = "Crear mantención",
            description = "Registra una nueva mantención."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Mantención creada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Mantencion.class))
    )
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @PostMapping
    public ResponseEntity<Mantencion> crearMantencion(@RequestBody Mantencion mantencion) {
        Mantencion nuevaMantencion = mantencionService.guardarMantencion(mantencion);
        return new ResponseEntity<>(nuevaMantencion, HttpStatus.CREATED); // Retorna 201 Created explícito
    }

    @Operation(
            summary = "Eliminar mantención",
            description = "Elimina una mantención mediante su ID."
    )
    @ApiResponse(responseCode = "204", description = "Mantención eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Mantención no encontrada")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMantencion(
            @Parameter(description = "ID de la mantención a eliminar", example = "1") // <-- Exigencia Punto 5
            @PathVariable Long id
    ) {
        mantencionService.eliminarMantencion(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content real
    }
}