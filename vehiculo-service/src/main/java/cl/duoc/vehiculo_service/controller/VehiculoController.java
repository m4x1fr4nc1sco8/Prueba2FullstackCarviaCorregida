package cl.duoc.vehiculo_service.controller;

import cl.duoc.vehiculo_service.model.Vehiculo;
import cl.duoc.vehiculo_service.service.VehiculoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    // LISTAR TODOS LOS VEHICULOS
    @GetMapping
    public List<Vehiculo> obtenerVehiculos() {
        return vehiculoService.obtenerVehiculos();
    }

    // OBTENER VEHICULO POR ID
    @GetMapping("/{id}")
    public Optional<Vehiculo> obtenerVehiculoPorId(@PathVariable Long id) {
        return vehiculoService.obtenerVehiculoPorId(id);
    }

    // CREAR VEHICULO
    @PostMapping
    public Vehiculo guardarVehiculo(@RequestBody Vehiculo vehiculo) {
        return vehiculoService.guardarVehiculo(vehiculo);
    }

    // ACTUALIZAR VEHICULO
    @PutMapping("/{id}")
    public Vehiculo actualizarVehiculo(@PathVariable Long id,
                                       @RequestBody Vehiculo vehiculoActualizado) {

        return vehiculoService.actualizarVehiculo(id, vehiculoActualizado);
    }

    // ELIMINAR VEHICULO
    @DeleteMapping("/{id}")
    public void eliminarVehiculo(@PathVariable Long id) {
        vehiculoService.eliminarVehiculo(id);
    }
}