package cl.duoc.notificacion_service.repository;

import cl.duoc.notificacion_service.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByUsuarioId(Long usuarioId);

    List<Notificacion> findByTipoNotificacionIgnoreCase(String tipoNotificacion);

}