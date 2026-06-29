package cl.duoc.sucursal_service;

import cl.duoc.sucursal_service.exception.SucursalNotExistException;
import cl.duoc.sucursal_service.model.Sucursal;
import cl.duoc.sucursal_service.repository.SucursalRepository;
import cl.duoc.sucursal_service.service.SucursalService;
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
@DisplayName("Pruebas unitarias para SucursalService")
public class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalService sucursalService;

    private Sucursal sucursal;

    @BeforeEach
    void setUp() {
        sucursal = new Sucursal();
        sucursal.setId(1L);
        sucursal.setNombreSucursal("Sucursal Plaza Vespucio");
        sucursal.setDireccion("Av. Vicuña Mackenna 7110");
        sucursal.setCiudad("Santiago");
        sucursal.setTelefono("+56912345678");
        sucursal.setHorarioAtencion("09:00 a 18:00");
        sucursal.setEstadoSucursal("Activa");
    }

    @Test
    @DisplayName("Debe listar todas las sucursales correctamente")
    void listarTodas_deberiaRetornarListaDeSucursales() {
        when(sucursalRepository.findAll()).thenReturn(Arrays.asList(sucursal));

        List<Sucursal> resultado = sucursalService.obtenerSucursales();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(sucursalRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar una sucursal por ID cuando existe")
    void buscarPorId_cuandoExiste_deberiaRetornarSucursal() {
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));

        Sucursal resultado = sucursalService.buscarSucursalPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Sucursal Plaza Vespucio", resultado.getNombreSucursal());
        verify(sucursalRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar SucursalNotExistException cuando la sucursal no existe")
    void buscarPorId_cuandoNoExiste_deberiaLanzarSucursalNotExistException() {
        when(sucursalRepository.findById(99L)).thenReturn(Optional.empty());

        SucursalNotExistException exception = assertThrows(
                SucursalNotExistException.class,
                () -> sucursalService.buscarSucursalPorId(99L)
        );

        assertEquals("Sucursal no encontrada", exception.getMessage());
        verify(sucursalRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar una sucursal correctamente")
    void guardar_deberiaRetornarSucursalGuardada() {
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursal);

        Sucursal resultado = sucursalService.guardarSucursal(new Sucursal());

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(sucursalRepository).save(any(Sucursal.class));
    }

    @Test
    @DisplayName("Debe eliminar una sucursal por ID correctamente")
    void eliminar_deberiaEjecutarEliminacion() {
        doNothing().when(sucursalRepository).deleteById(1L);

        String resultado = sucursalService.eliminarSucursal(1L);

        assertNotNull(resultado);
        verify(sucursalRepository).deleteById(1L);
    }
}