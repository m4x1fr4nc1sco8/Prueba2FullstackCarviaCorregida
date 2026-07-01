package cl.duoc.seguro_service.controller;

import cl.duoc.seguro_service.exception.SeguroNotExistException;
import cl.duoc.seguro_service.model.Seguro;
import cl.duoc.seguro_service.service.SeguroService;
import cl.duoc.seguro_service.feign.VehiculoFeignClient; // <-- Importación del cliente Feign
import cl.duoc.seguro_service.dto.VehiculoDTO; // <-- Importación del DTO del Vehículo

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired; // <-- Para inyectar Feign
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

    @Autowired
    private VehiculoFeignClient vehiculoFeignClient; // <-- Inyectamos Feign en el Controller

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
        return ResponseEntity.ok(seguros);
    }

    @Operation(
            summary = "Obtener seguro",
            description = "Obtiene un seguro mediante su ID y consulta en red los datos de su vehículo."
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
            @Parameter(description = "ID único del seguro a buscar", example = "1")
            @PathVariable Long id
    ) {
        // 1. Llamamos a tu servicio original (Devuelve entidad Seguro, todo en verde en tus Tests)
        Seguro seguro = seguroService.buscarSeguroPorId(id);
        if (seguro == null) {
            throw new SeguroNotExistException("Seguro no encontrado");
        }

// 2. Buscamos el vehiculo mediante Feign Client y se lo asignamos al objeto antes de responder
        if (seguro.getVehiculoId() != null) {
            try {
                VehiculoDTO vehiculo = vehiculoFeignClient.obtenerVehiculoPorId(seguro.getVehiculoId());

                seguro.setVehiculos(java.util.List.of(vehiculo));
            } catch (Exception e) {

                seguro.setVehiculos(null);
            }
        }

        return ResponseEntity.ok(seguro);
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
        return new ResponseEntity<>(nuevoSeguro, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Eliminar seguro",
            description = "Elimina un seguro mediante su ID."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Seguro eliminado correctamente"
    )
    @ApiResponse(responseCode = "404", description = "Seguro no encontrado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSeguro(
            @Parameter(description = "ID del seguro a eliminar", example = "1")
            @PathVariable Long id
    ) {
        seguroService.eliminarSeguro(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Obtener seguro por ID de Vehículo",
            description = "Busca el seguro asociado a un vehículo específico."
    )
    @ApiResponse(responseCode = "200", description = "Seguro encontrado")
    @ApiResponse(responseCode = "404", description = "Seguro no encontrado para este vehículo")
    @GetMapping("/vehiculo/{vehiculoId}")
    public ResponseEntity<Seguro> obtenerSeguroPorVehiculoId(@PathVariable Long vehiculoId) {
        Seguro seguro = seguroService.buscarSeguroPorVehiculoId(vehiculoId);

        if (seguro == null) {
            return ResponseEntity.notFound().build();
        }

        // Inyectamos también los datos aquí si se busca directamente por el ID del auto
        try {
            VehiculoDTO vehiculo = vehiculoFeignClient.obtenerVehiculoPorId(vehiculoId);
            seguro.setVehiculos(java.util.List.of(vehiculo));
        } catch (Exception e) {
            seguro.setVehiculos(null);
        }

        return ResponseEntity.ok(seguro);
    }
}