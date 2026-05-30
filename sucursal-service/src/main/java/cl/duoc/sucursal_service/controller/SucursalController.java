package cl.duoc.sucursal_service.controller;

import cl.duoc.sucursal_service.model.Sucursal;
import cl.duoc.sucursal_service.service.SucursalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalController {

    private final SucursalService sucursalService;


    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }


    // Obtener todas las sucursales
    @GetMapping
    public List<Sucursal> obtenerSucursales() {
        return sucursalService.obtenerSucursales();
    }


    // Obtener sucursal por ID
    @GetMapping("/{id}")
    public Sucursal obtenerSucursalPorId(@PathVariable Long id) {
        return sucursalService.buscarSucursalPorId(id);
    }


    // Crear sucursal
    @PostMapping
    public Sucursal crearSucursal(@RequestBody Sucursal sucursal) {
        return sucursalService.guardarSucursal(sucursal);
    }


    // Eliminar sucursal
    @DeleteMapping("/{id}")
    public String eliminarSucursal(@PathVariable Long id) {
        return sucursalService.eliminarSucursal(id);
    }

}