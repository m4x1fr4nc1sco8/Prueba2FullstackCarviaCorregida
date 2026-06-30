package cl.duoc.notificacion_service.controller;

import cl.duoc.notificacion_service.exception.NotificacionNotExistException;
import cl.duoc.notificacion_service.model.Notificacion;
import cl.duoc.notificacion_service.service.NotificacionService;

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
                    array = @ArraySchema(schema = @Schema(implementation = Notificacion.class))
            )
    )
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping
    public ResponseEntity<List<Notificacion>> obtenerNotificaciones() {
        List<Notificacion> notificaciones = notificacionService.obtenerNotificaciones();
        return ResponseEntity.ok(notificaciones);
    }

    // <-- NUEVO ENDPOINT PARA EL PUNTO 2 (Sincrónico con Feign)
    @Operation(
            summary = "Obtener por Usuario ID",
            description = "Obtiene todas las notificaciones enviadas a un usuario específico."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Notificaciones del usuario obtenidas con éxito",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Notificacion.class)))
    )
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesPorUsuarioId(
            @Parameter(description = "ID del usuario/cliente a consultar", example = "1")
            @PathVariable Long usuarioId
    ) {

        List<Notificacion> notificaciones = notificacionService.obtenerNotificacionesPorUsuarioId(usuarioId);
        return ResponseEntity.ok(notificaciones);
    }

    @Operation(
            summary = "Obtener notificación",
            description = "Obtiene una notificación mediante su ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Notificación encontrada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Notificacion.class))
    )
    @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> obtenerNotificacionPorId(
            @Parameter(description = "ID único de la notificación a buscar", example = "1")
            @PathVariable Long id
    ) {
        Notificacion notificacion = notificacionService.buscarNotificacionPorId(id);
        if (notificacion == null) {
            throw new NotificacionNotExistException("Notificación no encontrada");
        }
        return ResponseEntity.ok(notificacion);
    }

    @Operation(
            summary = "Crear notificación",
            description = "Registra una nueva notificación en el sistema."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Notificación creada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Notificacion.class))
    )
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @PostMapping
    public ResponseEntity<Notificacion> crearNotificacion(@RequestBody Notificacion notificacion) {
        Notificacion nuevaNotificacion = notificacionService.guardarNotificacion(notificacion);
        return new ResponseEntity<>(nuevaNotificacion, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Eliminar notificación",
            description = "Elimina una notificación mediante su ID."
    )
    @ApiResponse(responseCode = "204", description = "Notificación eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(
            @Parameter(description = "ID de la notificación a eliminar", example = "1")
            @PathVariable Long id
    ) {
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}