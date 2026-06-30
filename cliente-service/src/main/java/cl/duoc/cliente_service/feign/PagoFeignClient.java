package cl.duoc.cliente_service.feign;

import cl.duoc.cliente_service.dto.PagoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "pago-service", path = "/api/v1/pagos")
public interface PagoFeignClient {

    @GetMapping("/cliente/{clienteId}")
    List<PagoDTO> obtenerPagosPorClienteId(@PathVariable("clienteId") Long clienteId);
}