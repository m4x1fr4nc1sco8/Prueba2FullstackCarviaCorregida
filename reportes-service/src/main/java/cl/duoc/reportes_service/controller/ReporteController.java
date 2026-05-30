package cl.duoc.reportes_service.controller;

import cl.duoc.reportes_service.dto.ClienteDTO;
import cl.duoc.reportes_service.dto.VehiculoDTO;
import cl.duoc.reportes_service.feign.ClienteFeignClient;
import cl.duoc.reportes_service.feign.VehiculoFeignClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    @Autowired
    private ClienteFeignClient clienteFeignClient;

    @Autowired
    private VehiculoFeignClient vehiculoFeignClient;

    // LISTADO CLIENTES
    @GetMapping("/clientes")
    public List<ClienteDTO> obtenerClientes() {

        return clienteFeignClient.obtenerClientes();
    }

    @GetMapping("/vehiculos")
    public List<VehiculoDTO> obtenerVehiculos() {
        return vehiculoFeignClient.obtenerVehiculos();
    }
}