package cl.duoc.notificacion_service.service;

import cl.duoc.notificacion_service.model.Notificacion;
import cl.duoc.notificacion_service.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;


    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }


    // Obtener todas las notificaciones
    public List<Notificacion> obtenerNotificaciones() {
        return notificacionRepository.findAll();
    }


    // Buscar notificación por ID
    public Notificacion buscarNotificacionPorId(Long id) {
        return notificacionRepository.findById(id)
                .orElseThrow(() -> new cl.duoc.notificacion_service.exception.NotificacionNotExistException("Notificación no encontrada"));
    }


    // Guardar notificación
    public Notificacion guardarNotificacion(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }


    // Eliminar notificación
    public String eliminarNotificacion(Long id) {

        notificacionRepository.deleteById(id);

        return "Notificación eliminada correctamente";
    }


    public List<Notificacion> obtenerNotificacionesPorUsuarioId(Long usuarioId) {
        return notificacionRepository.findByUsuarioId(usuarioId);
    }



}