package cl.duoc.seguro_service.controller;

import cl.duoc.seguro_service.exception.SeguroNotExistException;
import cl.duoc.seguro_service.model.Seguro;
import cl.duoc.seguro_service.service.SeguroService;
import io.swagger.v3.oas.annotations.Operation;
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
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = Seguro.class
                            )
                    )
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @GetMapping
    public List<Seguro> obtenerSeguros() {

        return seguroService.obtenerSeguros();

    }

    @Operation(
            summary = "Obtener seguro",
            description = "Obtiene un seguro mediante su ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Seguro encontrado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = Seguro.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Seguro no encontrado"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Seguro> obtenerSeguroPorId(@PathVariable Long id) {
        try {
            Seguro seguro = seguroService.buscarSeguroPorId(id);
            return ResponseEntity.ok(seguro);
        } catch (SeguroNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Crear seguro",
            description = "Registra un nuevo seguro en el sistema."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Seguro creado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = Seguro.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Seguro crearSeguro(
            @RequestBody Seguro seguro
    ) {

        return seguroService.guardarSeguro(seguro);

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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String eliminarSeguro(
            @PathVariable Long id
    ) {

        return seguroService.eliminarSeguro(id);

    }

}