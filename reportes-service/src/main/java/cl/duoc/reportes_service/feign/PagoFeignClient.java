package cl.duoc.reportes_service.feign;

import cl.duoc.reportes_service.dto.PagoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "pago-service", path = "/api/v1/pagos")
public interface PagoFeignClient {

    @GetMapping
    List<PagoDTO> obtenerPagos();
}
