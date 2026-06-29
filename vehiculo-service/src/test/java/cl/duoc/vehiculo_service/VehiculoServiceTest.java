package cl.duoc.vehiculo_service;

import cl.duoc.vehiculo_service.model.Vehiculo;
import cl.duoc.vehiculo_service.repository.VehiculoRepository;
import cl.duoc.vehiculo_service.service.VehiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para VehiculoService")
public class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @InjectMocks
    private VehiculoService vehiculoService;

    private Vehiculo vehiculo;

    @BeforeEach
    void setUp() {
        vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setPatente("ABCD12");
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Yaris");
        vehiculo.setAnio(2022);
        vehiculo.setColor("Rojo");
        vehiculo.setSucursalId(2L);
        vehiculo.setTipoVehiculo("Sedan");
    }

    @Test
    @DisplayName("Debe listar todos los vehículos correctamente")
    void listarTodos_deberiaRetornarListaDeVehiculos() {
        when(vehiculoRepository.findAll()).thenReturn(Arrays.asList(vehiculo));

        List<Vehiculo> resultado = vehiculoService.obtenerVehiculos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(vehiculoRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar un vehículo por ID cuando existe")
    void buscarPorId_cuandoExiste_deberiaRetornarOptionalConVehiculo() {
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));

        Optional<Vehiculo> resultado = vehiculoService.obtenerVehiculoPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("ABCD12", resultado.get().getPatente());
        verify(vehiculoRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe retornar un Optional vacío cuando el vehículo no existe")
    void buscarPorId_cuandoNoExiste_deberiaRetornarOptionalVacio() {
        when(vehiculoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Vehiculo> resultado = vehiculoService.obtenerVehiculoPorId(99L);

        assertFalse(resultado.isPresent());
        verify(vehiculoRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar un vehículo correctamente")
    void guardar_deberiaRetornarVehiculoGuardado() {
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);

        Vehiculo resultado = vehiculoService.guardarVehiculo(new Vehiculo());

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(vehiculoRepository).save(any(Vehiculo.class));
    }

    @Test
    @DisplayName("Debe eliminar un vehículo por ID correctamente")
    void eliminar_deberiaEjecutarEliminacion() {
        doNothing().when(vehiculoRepository).deleteById(1L);

        vehiculoService.eliminarVehiculo(1L);

        verify(vehiculoRepository).deleteById(1L);
    }
}