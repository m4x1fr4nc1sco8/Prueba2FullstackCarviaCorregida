package cl.duoc.vehiculo_service.controller;

import cl.duoc.vehiculo_service.model.Vehiculo;
import cl.duoc.vehiculo_service.service.VehiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(
        name = "Vehiculos",
        description = "Operaciones disponibles para la gestión de vehiculos"
)

@RestController
@RequestMapping("/api/v1/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;


    @Operation(
            summary = "Listar vehículos",
            description = "Obtiene el listado completo de vehículos registrados."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Vehículos listados correctamente",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = Vehiculo.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @GetMapping
    public List<Vehiculo> obtenerVehiculos() {
        return vehiculoService.obtenerVehiculos();
    }

    @Operation(
            summary = "Obtener vehículo",
            description = "Obtiene un vehículo mediante su ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Vehículo encontrado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Vehiculo.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Vehículo no encontrado"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtenerVehiculoPorId(@PathVariable Long id) {
        try {
            Vehiculo vehiculo = vehiculoService.obtenerVehiculoPorId(id)
                    .orElseThrow(() -> new cl.duoc.vehiculo_service.exception.VehiculoNotExistException("Vehículo no encontrado"));
            return ResponseEntity.ok(vehiculo);
        } catch (cl.duoc.vehiculo_service.exception.VehiculoNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Crear vehículo",
            description = "Registra un nuevo vehículo."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Vehículo creado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Vehiculo.class)
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
    public Vehiculo guardarVehiculo(
            @RequestBody Vehiculo vehiculo
    ) {
        return vehiculoService.guardarVehiculo(vehiculo);
    }


    @Operation(
            summary = "Actualizar vehículo",
            description = "Actualiza la información de un vehículo existente."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Vehículo actualizado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Vehiculo.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Vehículo no encontrado"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @PutMapping("/{id}")
    public Vehiculo actualizarVehiculo(
            @PathVariable Long id,
            @RequestBody Vehiculo vehiculoActualizado
    ) {

        return vehiculoService.actualizarVehiculo(id, vehiculoActualizado);

    }


    @Operation(
            summary = "Eliminar vehículo",
            description = "Elimina un vehículo mediante su ID."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Vehículo eliminado correctamente"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Vehículo no encontrado"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarVehiculo(
            @PathVariable Long id
    ) {

        vehiculoService.eliminarVehiculo(id);

    }
}