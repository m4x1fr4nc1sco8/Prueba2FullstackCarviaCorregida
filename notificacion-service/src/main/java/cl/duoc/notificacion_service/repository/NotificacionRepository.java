package cl.duoc.notificacion_service.repository;

import cl.duoc.notificacion_service.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    // Genera automáticamente la query: SELECT * FROM notificaciones WHERE usuario_id = ?
    List<Notificacion> findByUsuarioId(Long usuarioId);
}