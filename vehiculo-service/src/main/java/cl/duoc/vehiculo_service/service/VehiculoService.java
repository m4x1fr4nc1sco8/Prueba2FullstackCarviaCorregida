package cl.duoc.vehiculo_service.service;

import cl.duoc.vehiculo_service.model.Vehiculo;
import cl.duoc.vehiculo_service.repository.VehiculoRepository;
import cl.duoc.vehiculo_service.feign.SucursalFeignClient;
import cl.duoc.vehiculo_service.feign.SeguroFeignClient;
import cl.duoc.vehiculo_service.feign.MantencionFeignClient;
import cl.duoc.vehiculo_service.dto.SucursalDTO;
import cl.duoc.vehiculo_service.dto.SeguroDTO;
import cl.duoc.vehiculo_service.dto.MantencionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final SucursalFeignClient sucursalFeignClient;
    private final SeguroFeignClient seguroFeignClient;
    private final MantencionFeignClient mantencionFeignClient;

    // Inyección limpia por constructor
    public VehiculoService(VehiculoRepository vehiculoRepository,
                           SucursalFeignClient sucursalFeignClient,
                           SeguroFeignClient seguroFeignClient,
                           MantencionFeignClient mantencionFeignClient) {
        this.vehiculoRepository = vehiculoRepository;
        this.sucursalFeignClient = sucursalFeignClient;
        this.seguroFeignClient = seguroFeignClient;
        this.mantencionFeignClient = mantencionFeignClient;
    }

    // LISTAR TODOS LOS VEHICULOS
    public List<Vehiculo> obtenerVehiculos() {
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();

        // Recorremos la lista para completar los datos dinámicos usando Feign
        for (Vehiculo vehiculo : vehiculos) {
            completarDatosVehiculo(vehiculo);
        }

        return vehiculos;
    }

    // OBTENER VEHICULO POR ID
    public Optional<Vehiculo> obtenerVehiculoPorId(Long id) {
        Optional<Vehiculo> vehiculoOpt = vehiculoRepository.findById(id);

        if (vehiculoOpt.isPresent()) {
            completarDatosVehiculo(vehiculoOpt.get());
        }

        return vehiculoOpt;
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
            vehiculo.setTipovehiculo(vehiculoActualizado.getTipovehiculo());
            vehiculo.setSucursalId(vehiculoActualizado.getSucursalId());

            return vehiculoRepository.save(vehiculo);
        }

        return null;
    }

    // ELIMINAR VEHICULO
    public void eliminarVehiculo(Long id) {
        vehiculoRepository.deleteById(id);
    }

    private void completarDatosVehiculo(Vehiculo vehiculo) {
        // 1. Obtener datos de la Sucursal
        if (vehiculo.getSucursalId() != null) {
            try {
                SucursalDTO sucursal = sucursalFeignClient.obtenerSucursalPorId(vehiculo.getSucursalId());
                vehiculo.setSucursal(sucursal);
            } catch (Exception e) {
                System.out.println("Error al traer sucursal: " + e.getMessage());
                vehiculo.setSucursal(null);
            }
        }

        // === 2. OBTENER DATOS DEL SEGURO ===
        try {
            ResponseEntity<SeguroDTO> response = seguroFeignClient.obtenerSeguroPorVehiculoId(vehiculo.getId());
            if (response != null && response.getBody() != null) {
                vehiculo.setSeguro(response.getBody());
            } else {
                vehiculo.setSeguro(null);
            }
        } catch (Exception e) {
            // CAMBIA LA LÍNEA DE ABAJO PARA VER EL ERROR REAL:
            e.printStackTrace();
            vehiculo.setSeguro(null);
        }

        // 3. Obtener el historial de Mantenciones
        try {
            // Volvemos a usar getId() aquí también
            List<MantencionDTO> mantenciones = mantencionFeignClient.obtenerMantencionesPorVehiculoId(vehiculo.getId());
            if (mantenciones != null) {
                vehiculo.setMantenciones(mantenciones);
            } else {
                vehiculo.setMantenciones(new ArrayList<>());
            }
        } catch (Exception e) {
            System.out.println("Error al traer mantenciones: " + e.getMessage());
            vehiculo.setMantenciones(new ArrayList<>());
        }
    }
}