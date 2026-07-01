package cl.duoc.seguro_service.service;

import cl.duoc.seguro_service.model.Seguro;
import cl.duoc.seguro_service.repository.SeguroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeguroService {

    private final SeguroRepository seguroRepository;

    public SeguroService(SeguroRepository seguroRepository) {
        this.seguroRepository = seguroRepository;
    }

    // Obtener todos los seguros
    public List<Seguro> obtenerSeguros() {
        return seguroRepository.findAll();
    }

    // Buscar seguro por ID
    public Seguro buscarSeguroPorId(Long id) {
        return seguroRepository.findById(id)
                .orElseThrow(() -> new cl.duoc.seguro_service.exception.SeguroNotExistException("Seguro no encontrado"));
    }

    // NUEVO MÉTODO: Buscar seguro por el ID del vehículo
    public Seguro buscarSeguroPorVehiculoId(Long vehiculoId) {
        List<Seguro> seguros = seguroRepository.findByVehiculoId(vehiculoId);
        return seguros.isEmpty() ? null : seguros.get(0); // Retorna el primero que encuentre de forma segura
    }

    // Guardar seguro
    public Seguro guardarSeguro(Seguro seguro) {
        return seguroRepository.save(seguro);
    }

    // Eliminar seguro
    public String eliminarSeguro(Long id) {
        seguroRepository.deleteById(id);
        return "Seguro eliminado correctamente";
    }
}