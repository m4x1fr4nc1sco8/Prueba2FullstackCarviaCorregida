package cl.duoc.seguro_service.controller;

import cl.duoc.seguro_service.model.Seguro;
import cl.duoc.seguro_service.service.SeguroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seguros")
public class SeguroController {

    private final SeguroService seguroService;


    public SeguroController(SeguroService seguroService) {
        this.seguroService = seguroService;
    }


    // Obtener todos los seguros
    @GetMapping
    public List<Seguro> obtenerSeguros() {
        return seguroService.obtenerSeguros();
    }


    // Obtener seguro por ID
    @GetMapping("/{id}")
    public Seguro obtenerSeguroPorId(@PathVariable Long id) {
        return seguroService.buscarSeguroPorId(id);
    }


    // Crear seguro
    @PostMapping
    public Seguro crearSeguro(@RequestBody Seguro seguro) {
        return seguroService.guardarSeguro(seguro);
    }


    // Eliminar seguro
    @DeleteMapping("/{id}")
    public String eliminarSeguro(@PathVariable Long id) {
        return seguroService.eliminarSeguro(id);
    }

}