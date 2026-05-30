package cl.duoc.mantencion_service.service;

import cl.duoc.mantencion_service.model.Mantencion;
import cl.duoc.mantencion_service.repository.MantencionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MantencionService {

    private final MantencionRepository mantencionRepository;


    public MantencionService(MantencionRepository mantencionRepository) {
        this.mantencionRepository = mantencionRepository;
    }


    // Obtener todas las mantenciones
    public List<Mantencion> obtenerMantenciones() {
        return mantencionRepository.findAll();
    }


    // Buscar mantención por ID
    public Mantencion buscarMantencionPorId(Long id) {

        Optional<Mantencion> mantencion = mantencionRepository.findById(id);

        return mantencion.orElse(null);
    }


    // Guardar mantención
    public Mantencion guardarMantencion(Mantencion mantencion) {
        return mantencionRepository.save(mantencion);
    }


    // Eliminar mantención
    public String eliminarMantencion(Long id) {

        mantencionRepository.deleteById(id);

        return "Mantención eliminada correctamente";
    }

}