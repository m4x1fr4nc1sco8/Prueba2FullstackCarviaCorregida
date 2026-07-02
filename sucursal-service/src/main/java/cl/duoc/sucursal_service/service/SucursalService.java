package cl.duoc.sucursal_service.service;

import cl.duoc.sucursal_service.model.Sucursal;
import cl.duoc.sucursal_service.repository.SucursalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SucursalService {

    private final SucursalRepository sucursalRepository;


    public SucursalService(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }


    // Obtener todas las sucursales
    public List<Sucursal> obtenerSucursales() {
        return sucursalRepository.findAll();
    }


    // Buscar sucursal por ID
    public Sucursal buscarSucursalPorId(Long id) {
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new cl.duoc.sucursal_service.exception.SucursalNotExistException("Sucursal no encontrada"));
    }


    // Guardar sucursal
    public Sucursal guardarSucursal(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }


    // Eliminar sucursal
    public String eliminarSucursal(Long id) {

        sucursalRepository.deleteById(id);

        return "Sucursal eliminada correctamente";
    }

    public List<Sucursal> buscarPorEstado(String estado) {
        return sucursalRepository.findByEstadoSucursalIgnoreCase(estado);
    }

}