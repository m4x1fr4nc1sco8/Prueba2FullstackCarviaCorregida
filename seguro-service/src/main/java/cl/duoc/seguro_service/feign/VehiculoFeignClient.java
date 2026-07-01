package cl.duoc.seguro_service.feign;

import cl.duoc.seguro_service.dto.VehiculoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vehiculo-service", path = "/api/v1/vehiculos")
public interface VehiculoFeignClient {

    @GetMapping("/{id}")
    VehiculoDTO obtenerVehiculoPorId(@PathVariable("id") Long id);
}