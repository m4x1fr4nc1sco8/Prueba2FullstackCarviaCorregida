package cl.duoc.seguro_service;

import cl.duoc.seguro_service.exception.SeguroNotExistException;
import cl.duoc.seguro_service.model.Seguro;
import cl.duoc.seguro_service.repository.SeguroRepository;
import cl.duoc.seguro_service.service.SeguroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para SeguroService")
public class SeguroServiceTest {

    @Mock
    private SeguroRepository seguroRepository;

    @InjectMocks
    private SeguroService seguroService;

    private Seguro seguro;

    @BeforeEach
    void setUp() {
        seguro = new Seguro();
        seguro.setId(1L);
        seguro.setVehiculoId(10L);
        seguro.setNombreSeguro("Seguro Total Carvia");
        seguro.setTipoCobertura("Cobertura Completa");
        seguro.setCostoSeguro(35000.0);
        seguro.setFechaInicio(LocalDate.now());
        seguro.setFechaFin(LocalDate.now().plusYears(1));
        seguro.setEstadoSeguro("Activo");
    }

    @Test
    @DisplayName("Debe listar todos los seguros correctamente")
    void listarTodos_deberiaRetornarListaDeSeguros() {
        when(seguroRepository.findAll()).thenReturn(Arrays.asList(seguro));

        List<Seguro> resultado = seguroService.obtenerSeguros();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(seguroRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar un seguro por ID cuando existe")
    void buscarPorId_cuandoExiste_deberiaRetornarSeguro() {
        when(seguroRepository.findById(1L)).thenReturn(Optional.of(seguro));

        Seguro resultado = seguroService.buscarSeguroPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Seguro Total Carvia", resultado.getNombreSeguro());
        verify(seguroRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar SeguroNotExistException cuando el seguro no existe")
    void buscarPorId_cuandoNoExiste_deberiaLanzarSeguroNotExistException() {
        when(seguroRepository.findById(99L)).thenReturn(Optional.empty());

        SeguroNotExistException exception = assertThrows(
                SeguroNotExistException.class,
                () -> seguroService.buscarSeguroPorId(99L)
        );

        assertEquals("Seguro no encontrado", exception.getMessage());
        verify(seguroRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar un seguro correctamente")
    void guardar_deberiaRetornarSeguroGuardado() {
        when(seguroRepository.save(any(Seguro.class))).thenReturn(seguro);

        Seguro resultado = seguroService.guardarSeguro(new Seguro());

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(seguroRepository).save(any(Seguro.class));
    }

    @Test
    @DisplayName("Debe eliminar un seguro por ID correctamente")
    void eliminar_deberiaEjecutarEliminacion() {
        doNothing().when(seguroRepository).deleteById(1L);

        String resultado = seguroService.eliminarSeguro(1L);

        assertNotNull(resultado);
        verify(seguroRepository).deleteById(1L);
    }
}