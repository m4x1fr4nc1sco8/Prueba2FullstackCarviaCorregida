package cl.duoc.pago_service.controller;

import cl.duoc.pago_service.model.Pago;
import cl.duoc.pago_service.service.PagoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    private final PagoService pagoService;


    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }


    // Obtener todos los pagos
    @GetMapping
    public List<Pago> obtenerPagos() {
        return pagoService.obtenerPagos();
    }


    // Obtener pago por ID
    @GetMapping("/{id}")
    public Pago obtenerPagoPorId(@PathVariable Long id) {
        return pagoService.buscarPagoPorId(id);
    }


    // Crear pago
    @PostMapping
    public Pago crearPago(@RequestBody Pago pago) {
        return pagoService.guardarPago(pago);
    }


    // Eliminar pago
    @DeleteMapping("/{id}")
    public String eliminarPago(@PathVariable Long id) {
        return pagoService.eliminarPago(id);
    }

}