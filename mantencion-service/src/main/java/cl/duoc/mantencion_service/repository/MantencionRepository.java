package cl.duoc.mantencion_service.repository;

import cl.duoc.mantencion_service.model.Mantencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MantencionRepository extends JpaRepository<Mantencion, Long> {

}