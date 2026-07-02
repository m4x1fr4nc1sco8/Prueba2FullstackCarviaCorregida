package cl.duoc.notificacion_service.controller;

import cl.duoc.notificacion_service.model.Notificacion;
import cl.duoc.notificacion_service.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Listar notificaciones", description = "Obtiene el listado completo de notificaciones del sistema.")
    @GetMapping
    public ResponseEntity<List<Notificacion>> listarNotificaciones() {
        // NOTA: Si en tu NotificacionService el método se llama ligeramente distinto,
        // ej: obtenerTodasNotificaciones() o listar(), ajusta el nombre aquí abajo.
        return ResponseEntity.ok(notificacionService.obtenerTodasNotificaciones());
    }

    @Operation(summary = "Crear notificación", description = "Registra una nueva notificación en el sistema.")
    @PostMapping
    public ResponseEntity<Notificacion> guardarNotificacion(@RequestBody Notificacion notificacion) {
        Notificacion nueva = notificacionService.guardarNotificacion(notificacion);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar notificación", description = "Elimina una notificación del sistema usando su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(
            @Parameter(description = "ID único de la notificación a eliminar", example = "1")
            @PathVariable Long id
    ) {
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Obtener notificaciones por Usuario ID",
            description = "Busca la lista de notificaciones asociadas a un ID de usuario específico en el sistema."
    )
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesPorUsuario(
            @Parameter(description = "ID único del usuario para buscar su historial", example = "30")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(notificacionService.obtenerNotificacionesPorUsuarioId(id));
    }

    @Operation(
            summary = "Filtrar notificaciones por tipo (Personalizado)",
            description = "Endpoint personalizado para listar notificaciones filtradas por su tipo de envío (Email o SMS)."
    )
    @GetMapping("/filtrar/tipo")
    public ResponseEntity<List<Notificacion>> filtrarPorTipo(
            @Parameter(description = "Tipo o medio de notificación", example = "Email")
            @RequestParam String tipo
    ) {
        return ResponseEntity.ok(notificacionService.buscarPorTipo(tipo));
    }
}