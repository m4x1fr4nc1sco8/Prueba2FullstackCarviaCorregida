package cl.duoc.pago_service.controller;

import cl.duoc.pago_service.exception.PagoNotExistException;
import cl.duoc.pago_service.model.Pago;
import cl.duoc.pago_service.service.PagoService;

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
        name = "Pagos",
        description = "Operaciones disponibles para la gestión de pagos"
)
@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Operation(
            summary = "Listar pagos",
            description = "Obtiene el listado completo de pagos registrados."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pagos listados correctamente",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Pago.class))
            )
    )
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping
    public ResponseEntity<List<Pago>> obtenerPagos() {
        List<Pago> pagos = pagoService.obtenerPagos();
        return ResponseEntity.ok(pagos);
    }

    // <-- NUEVO ENDPOINT PARA EL PUNTO 2 (Feign Client)
    @Operation(
            summary = "Obtener por Cliente ID",
            description = "Obtiene el historial de pagos asociados a un cliente específico."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pagos del cliente obtenidos correctamente",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Pago.class)))
    )
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pago>> obtenerPagosPorClienteId(
            @Parameter(description = "ID del cliente a consultar", example = "1")
            @PathVariable Long clienteId
    ) {
        List<Pago> pagos = pagoService.obtenerPagosPorClienteId(clienteId);
        return ResponseEntity.ok(pagos);
    }

    @Operation(
            summary = "Obtener pago",
            description = "Obtiene un pago mediante su ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pago encontrado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pago.class))
    )
    @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPagoPorId(
            @Parameter(description = "ID único del pago a buscar", example = "1")
            @PathVariable Long id
    ) {
        Pago pago = pagoService.buscarPagoPorId(id);
        if (pago == null) {
            throw new PagoNotExistException("Pago no encontrado");
        }
        return ResponseEntity.ok(pago);
    }

    @Operation(
            summary = "Crear pago",
            description = "Registra un nuevo pago en el sistema."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Pago creado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pago.class))
    )
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @PostMapping
    public ResponseEntity<Pago> crearPago(@RequestBody Pago pago) {
        Pago nuevoPago = pagoService.guardarPago(pago);
        return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Eliminar pago",
            description = "Elimina un pago mediante su ID."
    )
    @ApiResponse(responseCode = "204", description = "Pago eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(
            @Parameter(description = "ID del pago a eliminar", example = "1")
            @PathVariable Long id
    ) {
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }
}