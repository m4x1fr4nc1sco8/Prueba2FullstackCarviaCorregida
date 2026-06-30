package cl.duoc.pago_service.service;

import cl.duoc.pago_service.model.Pago;
import cl.duoc.pago_service.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;


    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }


    // Obtener todos los pagos
    public List<Pago> obtenerPagos() {
        return pagoRepository.findAll();
    }


    // Buscar pago por ID
    public Pago buscarPagoPorId(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new cl.duoc.pago_service.exception.PagoNotExistException("Pago no encontrado"));
    }


    // Guardar pago
    public Pago guardarPago(Pago pago) {
        return pagoRepository.save(pago);
    }


    // Eliminar pago
    public String eliminarPago(Long id) {

        pagoRepository.deleteById(id);

        return "Pago eliminado correctamente";
    }

    public List<Pago> obtenerPagosPorClienteId(Long clienteId) {
        return pagoRepository.findByClienteId(clienteId);
    }

}