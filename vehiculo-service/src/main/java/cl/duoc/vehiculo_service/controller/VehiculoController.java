package cl.duoc.vehiculo_service.controller;

import cl.duoc.vehiculo_service.exception.VehiculoNotExistException;
import cl.duoc.vehiculo_service.model.Vehiculo;
import cl.duoc.vehiculo_service.service.VehiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter; // <-- Importación para cumplir con el Punto 5
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
                    array = @ArraySchema(schema = @Schema(implementation = Vehiculo.class))
            )
    )
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping
    public ResponseEntity<List<Vehiculo>> obtenerVehiculos() {
        List<Vehiculo> vehiculos = vehiculoService.obtenerVehiculos();
        return ResponseEntity.ok(vehiculos); // Retorna 200 OK explícito
    }

    @Operation(
            summary = "Obtener vehículo",
            description = "Obtiene un vehículo mediante su ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Vehículo encontrado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Vehiculo.class))
    )
    @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtenerVehiculoPorId(
            @Parameter(description = "ID único del vehículo a buscar", example = "1") // <-- Exigencia Punto 5
            @PathVariable Long id
    ) {
        Vehiculo vehiculo = vehiculoService.obtenerVehiculoPorId(id)
                .orElseThrow(() -> new VehiculoNotExistException("Vehículo no encontrado"));
        return ResponseEntity.ok(vehiculo); // Retorna 200 OK
    }

    @Operation(
            summary = "Crear vehículo",
            description = "Registra un nuevo vehículo."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Vehículo creado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Vehiculo.class))
    )
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @PostMapping
    public ResponseEntity<Vehiculo> guardarVehiculo(@RequestBody Vehiculo vehiculo) {
        Vehiculo nuevoVehiculo = vehiculoService.guardarVehiculo(vehiculo);
        return new ResponseEntity<>(nuevoVehiculo, HttpStatus.CREATED); // Retorna 201 Created explícito
    }

    @Operation(
            summary = "Eliminar vehículo",
            description = "Elimina un vehículo mediante su ID."
    )
    @ApiResponse(responseCode = "204", description = "Vehículo eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVehiculo(
            @Parameter(description = "ID del vehículo a eliminar", example = "1") // <-- Exigencia Punto 5
            @PathVariable Long id
    ) {
        vehiculoService.eliminarVehiculo(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content real
    }

    // --- ENDPOINT PERSONALIZADO PARA FILTRAR POR TIPO DE VEHÍCULO ---
    @Operation(
            summary = "Filtrar vehículos por tipo (Personalizado)",
            description = "Endpoint personalizado para listar los vehículos filtrados por su tipo (ej: Sedan, SUV, Hatchback)."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Vehículos filtrados correctamente",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Vehiculo.class)))
    )
    @GetMapping("/filtrar/tipo")
    public ResponseEntity<List<Vehiculo>> filtrarPorTipo(
            @Parameter(description = "Tipo de vehículo a filtrar", example = "Sedan")
            @RequestParam String tipo
    ) {
        return ResponseEntity.ok(vehiculoService.buscarPorTipo(tipo));
    }
}