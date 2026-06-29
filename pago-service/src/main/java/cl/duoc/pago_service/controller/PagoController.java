package cl.duoc.pago_service.controller;

import cl.duoc.pago_service.model.Pago;
import cl.duoc.pago_service.service.PagoService;
import cl.duoc.pago_service.exception.PagoNotExistException;
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
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = Pago.class
                            )
                    )
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @GetMapping
    public List<Pago> obtenerPagos() {
        return pagoService.obtenerPagos();
    }

    @Operation(
            summary = "Obtener pago",
            description = "Obtiene un pago mediante su ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pago encontrado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = Pago.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Pago no encontrado"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtenerPagoPorId(@PathVariable Long id) {
        try {
            Pago pago = pagoService.buscarPagoPorId(id);
            return ResponseEntity.ok(pago);
        } catch (PagoNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Crear pago",
            description = "Registra un nuevo pago en el sistema."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Pago creado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = Pago.class
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
    public Pago crearPago(@RequestBody Pago pago) {
        return pagoService.guardarPago(pago);
    }

    @Operation(
            summary = "Eliminar pago",
            description = "Elimina un pago mediante su ID."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Pago eliminado correctamente"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Pago no encontrado"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String eliminarPago(@PathVariable Long id) {
        return pagoService.eliminarPago(id);
    }
}