package cl.duoc.sucursal_service;

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
public class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalService sucursalService;

    private Sucursal sucursalPrueba;

    @BeforeEach
    void setUp() {
        // Inicializamos el objeto Sucursal basándonos en tu modelo
        sucursalPrueba = new Sucursal(
                1L,
                "Sucursal Santiago Centro",
                "Av. Libertador Bernardo O'Higgins 123",
                "Santiago",
                "+56912345678",
                "09:00 - 18:00",
                "ACTIVA"
        );
    }

    @Test
    @DisplayName("Debería retornar todas las sucursales")
    void obtenerSucursales_DeberiaRetornarListaCompleta() {
        when(sucursalRepository.findAll()).thenReturn(Arrays.asList(sucursalPrueba));

        List<Sucursal> resultado = sucursalService.obtenerSucursales();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Sucursal Santiago Centro", resultado.get(0).getNombreSucursal());
        verify(sucursalRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería retornar una sucursal si el ID existe")
    void buscarSucursalPorId_CuandoExiste_DeberiaRetornarSucursal() {
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursalPrueba));

        Sucursal resultado = sucursalService.buscarSucursalPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Santiago", resultado.getCiudad());
        verify(sucursalRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería retornar null si el ID de la sucursal no existe")
    void buscarSucursalPorId_CuandoNoExiste_DeberiaRetornarNull() {
        when(sucursalRepository.findById(99L)).thenReturn(Optional.empty());

        Sucursal resultado = sucursalService.buscarSucursalPorId(99L);

        assertNull(resultado);
        verify(sucursalRepository, times(1)).findById(99L);
    }

}