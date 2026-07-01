package cl.duoc.vehiculo_service.feign;

import cl.duoc.vehiculo_service.dto.SeguroDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Corregimos el puerto a 9090 y quitamos 'path' para que no rompa la URL
@FeignClient(name = "seguro-service", url = "http://localhost:9090/api/v1/seguros")
public interface SeguroFeignClient {

    @GetMapping("/vehiculo/{vehiculoId}")
    ResponseEntity<SeguroDTO> obtenerSeguroPorVehiculoId(@PathVariable("vehiculoId") Long vehiculoId);
}