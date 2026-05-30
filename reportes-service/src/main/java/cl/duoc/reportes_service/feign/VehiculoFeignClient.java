package cl.duoc.reportes_service.feign;

import cl.duoc.reportes_service.dto.VehiculoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "vehiculo-service", path = "/api/v1/vehiculos")
public interface VehiculoFeignClient {

    @GetMapping
    List<VehiculoDTO> obtenerVehiculos();
}