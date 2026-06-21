package cl.duoc.vehiculo_service.service;

import cl.duoc.vehiculo_service.model.Vehiculo;
import cl.duoc.vehiculo_service.repository.VehiculoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    // LISTAR TODOS LOS VEHICULOS
    public List<Vehiculo> obtenerVehiculos() {
        return vehiculoRepository.findAll();
    }

    // OBTENER VEHICULO POR ID
    public Optional<Vehiculo> obtenerVehiculoPorId(Long id) {
        return vehiculoRepository.findById(id);
    }

    // CREAR VEHICULO
    public Vehiculo guardarVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    // ACTUALIZAR VEHICULO
    public Vehiculo actualizarVehiculo(Long id, Vehiculo vehiculoActualizado) {

        Vehiculo vehiculo = vehiculoRepository.findById(id).orElse(null);

        if (vehiculo != null) {

            vehiculo.setPatente(vehiculoActualizado.getPatente());
            vehiculo.setMarca(vehiculoActualizado.getMarca());
            vehiculo.setModelo(vehiculoActualizado.getModelo());
            vehiculo.setAnio(vehiculoActualizado.getAnio());
            vehiculo.setColor(vehiculoActualizado.getColor());
            vehiculo.setTipoVehiculo(vehiculoActualizado.getTipoVehiculo());

            return vehiculoRepository.save(vehiculo);
        }

        return null;
    }

    public void eliminarVehiculo(Long id) {
        vehiculoRepository.deleteById(id);
    }
}