package cl.duoc.seguro_service.repository;

import cl.duoc.seguro_service.model.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeguroRepository extends JpaRepository<Seguro, Long> {

    List<Seguro> findByVehiculoId(Long vehiculoId);
}