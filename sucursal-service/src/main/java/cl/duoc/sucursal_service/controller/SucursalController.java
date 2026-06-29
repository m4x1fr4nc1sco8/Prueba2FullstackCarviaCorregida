package cl.duoc.sucursal_service.controller;

import cl.duoc.sucursal_service.exception.SucursalNotExistException;
import cl.duoc.sucursal_service.model.Sucursal;
import cl.duoc.sucursal_service.service.SucursalService;
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
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = Sucursal.class
                            )
                    )
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @GetMapping
    public List<Sucursal> obtenerSucursales() {

        return sucursalService.obtenerSucursales();

    }


    @Operation(
            summary = "Obtener sucursal",
            description = "Obtiene una sucursal mediante su ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Sucursal encontrada correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = Sucursal.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Sucursal no encontrada"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> obtenerSucursalPorId(@PathVariable Long id) {
        try {
            Sucursal sucursal = sucursalService.buscarSucursalPorId(id);
            return ResponseEntity.ok(sucursal);
        } catch (SucursalNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Crear sucursal",
            description = "Registra una nueva sucursal en el sistema."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Sucursal creada correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = Sucursal.class
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
    public Sucursal crearSucursal(
            @RequestBody Sucursal sucursal
    ) {

        return sucursalService.guardarSucursal(sucursal);

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
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String eliminarSucursal(
            @PathVariable Long id
    ) {

        return sucursalService.eliminarSucursal(id);

    }

}