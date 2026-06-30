package cl.duoc.seguro_service.controller;

import cl.duoc.seguro_service.exception.SeguroNotExistException;
import cl.duoc.seguro_service.model.Seguro;
import cl.duoc.seguro_service.service.SeguroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter; // <-- Importación para cumplir con el Punto 5
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
        name = "Seguros",
        description = "Operaciones disponibles para la gestión de seguros"
)
@RestController
@RequestMapping("/api/v1/seguros")
public class SeguroController {

    private final SeguroService seguroService;

    public SeguroController(SeguroService seguroService) {
        this.seguroService = seguroService;
    }

    @Operation(
            summary = "Listar seguros",
            description = "Obtiene el listado completo de seguros registrados."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Seguros listados correctamente",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Seguro.class))
            )
    )
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping
    public ResponseEntity<List<Seguro>> obtenerSeguros() {
        List<Seguro> seguros = seguroService.obtenerSeguros();
        return ResponseEntity.ok(seguros); // Retorna 200 OK explícito
    }

    @Operation(
            summary = "Obtener seguro",
            description = "Obtiene un seguro mediante su ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Seguro encontrado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Seguro.class))
    )
    @ApiResponse(responseCode = "404", description = "Seguro no encontrado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping("/{id}")
    public ResponseEntity<Seguro> obtenerSeguroPorId(
            @Parameter(description = "ID único del seguro a buscar", example = "1") // <-- Exigencia Punto 5
            @PathVariable Long id
    ) {
        Seguro seguro = seguroService.buscarSeguroPorId(id);
        if (seguro == null) {
            throw new SeguroNotExistException("Seguro no encontrado");
        }
        return ResponseEntity.ok(seguro); // Retorna 200 OK
    }

    @Operation(
            summary = "Crear seguro",
            description = "Registra un nuevo seguro en el sistema."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Seguro creado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Seguro.class))
    )
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @PostMapping
    public ResponseEntity<Seguro> crearSeguro(@RequestBody Seguro seguro) {
        Seguro nuevoSeguro = seguroService.guardarSeguro(seguro);
        return new ResponseEntity<>(nuevoSeguro, HttpStatus.CREATED); // Retorna 201 Created explícito
    }

    @Operation(
            summary = "Eliminar seguro",
            description = "Elimina un seguro mediante su ID."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Seguro eliminado correctamente"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Seguro no encontrado"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSeguro(
            @Parameter(description = "ID del seguro a eliminar", example = "1") // <-- Exigencia Punto 5
            @PathVariable Long id
    ) {
        seguroService.eliminarSeguro(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content real
    }
}