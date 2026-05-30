package cl.duoc.mantencion_service.controller;

import cl.duoc.mantencion_service.model.Mantencion;
import cl.duoc.mantencion_service.service.MantencionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mantenciones")
public class MantencionController {

    private final MantencionService mantencionService;


    public MantencionController(MantencionService mantencionService) {
        this.mantencionService = mantencionService;
    }


    // Obtener todas las mantenciones
    @GetMapping
    public List<Mantencion> obtenerMantenciones() {
        return mantencionService.obtenerMantenciones();
    }


    // Obtener mantención por ID
    @GetMapping("/{id}")
    public Mantencion obtenerMantencionPorId(@PathVariable Long id) {
        return mantencionService.buscarMantencionPorId(id);
    }


    // Crear mantención
    @PostMapping
    public Mantencion crearMantencion(@RequestBody Mantencion mantencion) {
        return mantencionService.guardarMantencion(mantencion);
    }


    // Eliminar mantención
    @DeleteMapping("/{id}")
    public String eliminarMantencion(@PathVariable Long id) {
        return mantencionService.eliminarMantencion(id);
    }

}