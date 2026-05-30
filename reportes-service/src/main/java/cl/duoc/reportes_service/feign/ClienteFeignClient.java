package cl.duoc.reportes_service.feign;

import cl.duoc.reportes_service.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "cliente-service",
        path = "/api/v1/clientes"
)
public interface ClienteFeignClient {

    @GetMapping
    List<ClienteDTO> obtenerClientes();
}