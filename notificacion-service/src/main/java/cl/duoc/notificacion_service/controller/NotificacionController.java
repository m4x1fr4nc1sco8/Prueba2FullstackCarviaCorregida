package cl.duoc.notificacion_service.controller;

import cl.duoc.notificacion_service.model.Notificacion;
import cl.duoc.notificacion_service.service.NotificacionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;


    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }


    // Obtener todas las notificaciones
    @GetMapping
    public List<Notificacion> obtenerNotificaciones() {
        return notificacionService.obtenerNotificaciones();
    }


    // Obtener notificación por ID
    @GetMapping("/{id}")
    public Notificacion obtenerNotificacionPorId(@PathVariable Long id) {
        return notificacionService.buscarNotificacionPorId(id);
    }


    // Crear notificación
    @PostMapping
    public Notificacion crearNotificacion(@RequestBody Notificacion notificacion) {
        return notificacionService.guardarNotificacion(notificacion);
    }


    // Eliminar notificación
    @DeleteMapping("/{id}")
    public String eliminarNotificacion(@PathVariable Long id) {
        return notificacionService.eliminarNotificacion(id);
    }

}