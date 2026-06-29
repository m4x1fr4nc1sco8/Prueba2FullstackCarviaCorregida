package cl.duoc.mantencion_service.controller;

import cl.duoc.mantencion_service.model.Mantencion;
import cl.duoc.mantencion_service.service.MantencionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
                    array = @ArraySchema(
                            schema = @Schema(implementation = Mantencion.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @GetMapping
    public List<Mantencion> obtenerMantenciones() {
        return mantencionService.obtenerMantenciones();
    }


    @Operation(
            summary = "Obtener mantención",
            description = "Obtiene una mantención mediante su ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Mantención encontrada correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Mantencion.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Mantención no encontrada"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @GetMapping("/{id}")
    public org.springframework.http.ResponseEntity<Mantencion> obtenerMantencionPorId(@PathVariable Long id) {
        try {
            Mantencion mantencion = mantencionService.buscarMantencionPorId(id);
            return org.springframework.http.ResponseEntity.ok(mantencion);
        } catch (cl.duoc.mantencion_service.exception.MantencionNotExistException e) {

            return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).build();
        }
    }


    @Operation(
            summary = "Crear mantención",
            description = "Registra una nueva mantención."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Mantención creada correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Mantencion.class)
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
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Mantencion crearMantencion(@RequestBody Mantencion mantencion) {
        return mantencionService.guardarMantencion(mantencion);
    }

    @Operation(
            summary = "Eliminar mantención",
            description = "Elimina una mantención mediante su ID."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Mantención eliminada correctamente"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Mantención no encontrada"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public String eliminarMantencion(@PathVariable Long id) {
        return mantencionService.eliminarMantencion(id);
    }

}