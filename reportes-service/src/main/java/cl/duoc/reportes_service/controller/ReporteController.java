package cl.duoc.reportes_service.controller;

import cl.duoc.reportes_service.dto.ClienteDTO;
import cl.duoc.reportes_service.dto.VehiculoDTO;
import cl.duoc.reportes_service.feign.ClienteFeignClient;
import cl.duoc.reportes_service.feign.VehiculoFeignClient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "Reportes",
        description = "Operaciones disponibles para la gestión de reportes"
)
@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    @Autowired
    private ClienteFeignClient clienteFeignClient;

    @Autowired
    private VehiculoFeignClient vehiculoFeignClient;

    // REPORTE CLIENTES
    @Operation(
            summary = "Reporte de clientes",
            description = "Obtiene el listado consolidado de clientes desde el microservicio de clientes."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Clientes obtenidos correctamente",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ClienteDTO.class))
            )
    )
    @ApiResponse(responseCode = "404", description = "No se encontraron clientes")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteDTO>> obtenerClientes() {
        List<ClienteDTO> clientes = clienteFeignClient.obtenerClientes();
        return ResponseEntity.ok(clientes); // Retorna 200 OK explícito
    }

    // REPORTE VEHICULOS
    @Operation(
            summary = "Reporte de vehículos",
            description = "Obtiene el listado consolidado de vehículos desde el microservicio de vehículos."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Vehículos obtenidos correctamente",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = VehiculoDTO.class))
            )
    )
    @ApiResponse(responseCode = "404", description = "No se encontraron vehículos")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping("/vehiculos")
    public ResponseEntity<List<VehiculoDTO>> obtenerVehiculos() {
        // Quitamos el bloque try-catch, delegando excepciones Feign directamente al GlobalExceptionHandler
        List<VehiculoDTO> vehiculos = vehiculoFeignClient.obtenerVehiculos();
        return ResponseEntity.ok(vehiculos); // Retorna 200 OK explícito con tipo de dato estricto
    }
}