package cl.duoc.sucursal_service.repository;

import cl.duoc.sucursal_service.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

    // Agrega esto dentro de tu SucursalRepository antes de la llave de cierre }
    List<Sucursal> findByEstadoSucursalIgnoreCase(String estadoSucursal);

}