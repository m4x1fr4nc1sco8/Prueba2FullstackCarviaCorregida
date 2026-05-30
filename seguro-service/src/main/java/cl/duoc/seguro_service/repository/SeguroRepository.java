package cl.duoc.seguro_service.repository;

import cl.duoc.seguro_service.model.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguroRepository extends JpaRepository<Seguro, Long> {

}