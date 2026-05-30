package cl.duoc.seguro_service.service;

import cl.duoc.seguro_service.model.Seguro;
import cl.duoc.seguro_service.repository.SeguroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        Optional<Seguro> seguro = seguroRepository.findById(id);

        return seguro.orElse(null);
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