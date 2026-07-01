package cl.duoc.vehiculo_service.feign;

import cl.duoc.vehiculo_service.dto.SucursalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sucursal-service", path = "/api/v1/sucursales")
public interface SucursalFeignClient {

    @GetMapping("/{id}")
    SucursalDTO obtenerSucursalPorId(@PathVariable("id") Long id);
}