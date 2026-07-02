package cl.duoc.sucursal_service.controller;

import cl.duoc.sucursal_service.exception.SucursalNotExistException;
import cl.duoc.sucursal_service.model.Sucursal;
import cl.duoc.sucursal_service.service.SucursalService;

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
        name = "Sucursal",
        description = "Operaciones disponibles para la gestión de sucursales"
)
@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalController {

    private final SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @Operation(
            summary = "Listar sucursales",
            description = "Obtiene el listado completo de sucursales registradas."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Sucursales listadas correctamente",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Sucursal.class))
            )
    )
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping
    public ResponseEntity<List<Sucursal>> obtenerSucursales() {
        List<Sucursal> sucursales = sucursalService.obtenerSucursales();
        return ResponseEntity.ok(sucursales);
    }

    @Operation(
            summary = "Obtener sucursal",
            description = "Obtiene una sucursal mediante su ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Sucursal encontrada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sucursal.class))
    )
    @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> obtenerSucursalPorId(
            @Parameter(description = "ID único de la sucursal a buscar", example = "1")
            @PathVariable Long id
    ) {
        Sucursal sucursal = sucursalService.buscarSucursalPorId(id);
        if (sucursal == null) {
            throw new SucursalNotExistException("Sucursal no encontrada");
        }
        return ResponseEntity.ok(sucursal);
    }

    @Operation(
            summary = "Crear sucursal",
            description = "Registra una nueva sucursal en el sistema."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Sucursal creada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sucursal.class))
    )
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @PostMapping
    public ResponseEntity<Sucursal> crearSucursal(@RequestBody Sucursal sucursal) {
        Sucursal nuevaSucursal = sucursalService.guardarSucursal(sucursal);
        return new ResponseEntity<>(nuevaSucursal, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Eliminar sucursal",
            description = "Elimina una sucursal mediante su ID."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Sucursal eliminada correctamente"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Sucursal no encontrada"
    )
    @ApiResponse(
            responseCode = "500", description = "Error interno del servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(
            @Parameter(description = "ID de la sucursal a eliminar", example = "1")
            @PathVariable Long id
    ) {
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Filtrar sucursales por estado (Personalizado)",
            description = "Endpoint personalizado para listar las sucursales filtradas por su estado actual (ej: Activa, Inactiva)."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Sucursales filtradas correctamente",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Sucursal.class)))
    )
    @GetMapping("/filtrar/estado")
    public ResponseEntity<List<Sucursal>> filtrarPorEstado(
            @Parameter(description = "Estado de la sucursal a filtrar", example = "Activa")
            @RequestParam String estado
    ) {
        return ResponseEntity.ok(sucursalService.buscarPorEstado(estado));
    }
}