
package cl.duoc.cliente_service.repository;

import cl.duoc.cliente_service.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByApellidoContainingIgnoreCase(String apellido);

}