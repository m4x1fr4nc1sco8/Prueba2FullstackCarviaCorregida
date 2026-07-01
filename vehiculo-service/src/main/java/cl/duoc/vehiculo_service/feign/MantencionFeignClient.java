package cl.duoc.vehiculo_service.feign;

import cl.duoc.vehiculo_service.dto.MantencionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "mantencion-service", path = "/api/v1/mantenciones")
public interface MantencionFeignClient {

    @GetMapping("/vehiculo/{vehiculoId}")
    List<MantencionDTO> obtenerMantencionesPorVehiculoId(@PathVariable("vehiculoId") Long vehiculoId);
}