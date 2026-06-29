package cl.duoc.notificacion_service.controller;

import cl.duoc.notificacion_service.model.Notificacion;
import cl.duoc.notificacion_service.service.NotificacionService;
import cl.duoc.notificacion_service.exception.NotificacionNotExistException;
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
        name = "Notificaciones",
        description = "Operaciones disponibles para la gestión de notificaciones"
)
@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @Operation(
            summary = "Listar notificaciones",
            description = "Obtiene el listado completo de notificaciones registradas."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Notificaciones listadas correctamente",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(
                                    implementation = Notificacion.class
                            )
                    )
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @GetMapping
    public List<Notificacion> obtenerNotificaciones() {
        return notificacionService.obtenerNotificaciones();
    }

    @Operation(
            summary = "Obtener notificación",
            description = "Obtiene una notificación mediante su ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Notificación encontrada correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = Notificacion.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Notificación no encontrada"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> obtenerNotificacionPorId(@PathVariable Long id) {
        try {
            Notificacion notificacion = notificacionService.buscarNotificacionPorId(id);
            return ResponseEntity.ok(notificacion);
        } catch (NotificacionNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Crear notificación",
            description = "Registra una nueva notificación en el sistema."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Notificación creada correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = Notificacion.class
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
    public Notificacion crearNotificacion(@RequestBody Notificacion notificacion) {
        return notificacionService.guardarNotificacion(notificacion);
    }

    @Operation(
            summary = "Eliminar notificación",
            description = "Elimina una notificación mediante su ID."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Notificación eliminada correctamente"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Notificación no encontrada"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String eliminarNotificacion(@PathVariable Long id) {
        return notificacionService.eliminarNotificacion(id);
    }
}