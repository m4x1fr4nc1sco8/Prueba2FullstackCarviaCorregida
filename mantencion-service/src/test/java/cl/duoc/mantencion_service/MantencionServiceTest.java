package cl.duoc.mantencion_service;

import cl.duoc.mantencion_service.exception.MantencionNotExistException;
import cl.duoc.mantencion_service.model.Mantencion;
import cl.duoc.mantencion_service.repository.MantencionRepository;
import cl.duoc.mantencion_service.service.MantencionService;
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
@DisplayName("Pruebas unitarias para MantencionService")
public class MantencionServiceTest {

    @Mock
    private MantencionRepository mantencionRepository;

    @InjectMocks
    private MantencionService mantencionService;

    private Mantencion mantencion;

    @BeforeEach
    void setUp() {
        mantencion = new Mantencion();
        mantencion.setId(1L);
        mantencion.setVehiculoId(10L);
        mantencion.setDescripcion("Cambio de aceite y filtros");
        mantencion.setFechaMantencion(LocalDate.now());
        mantencion.setCosto(85000.0);
        mantencion.setTipoMantencion("Correctiva");
        mantencion.setEstadoMantencion("Completada");
    }

    @Test
    @DisplayName("Debe listar todas las mantenciones correctamente")
    void listarTodos_deberiaRetornarListaDeMantenciones() {
        when(mantencionRepository.findAll()).thenReturn(Arrays.asList(mantencion));

        List<Mantencion> resultado = mantencionService.obtenerMantenciones();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(mantencionRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar una mantención por ID cuando existe")
    void buscarPorId_cuandoExiste_deberiaRetornarMantencion() {
        when(mantencionRepository.findById(1L)).thenReturn(Optional.of(mantencion));

        Mantencion resultado = mantencionService.buscarMantencionPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Cambio de aceite y filtros", resultado.getDescripcion());
        verify(mantencionRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar MantencionNotExistException cuando la mantención no existe")
    void buscarPorId_cuandoNoExiste_deberiaLanzarMantencionNotExistException() {
        // Simulamos que el repositorio no encuentra nada (Optional vacío)
        when(mantencionRepository.findById(99L)).thenReturn(Optional.empty());

        // Verificamos que el servicio lance el error esperado (Igual que tu profesor)
        MantencionNotExistException exception = assertThrows(
                MantencionNotExistException.class,
                () -> mantencionService.buscarMantencionPorId(99L)
        );

        assertEquals("Mantención no encontrada", exception.getMessage());
        verify(mantencionRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar una mantención correctamente")
    void guardar_deberiaRetornarMantencionGuardada() {
        when(mantencionRepository.save(any(Mantencion.class))).thenReturn(mantencion);

        Mantencion resultado = mantencionService.guardarMantencion(new Mantencion());

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(mantencionRepository).save(any(Mantencion.class));
    }

    @Test
    @DisplayName("Debe eliminar una mantención por ID correctamente")
    void eliminar_deberiaEjecutarEliminacion() {
        doNothing().when(mantencionRepository).deleteById(1L);

        mantencionService.eliminarMantencion(1L);

        verify(mantencionRepository).deleteById(1L);
    }
}