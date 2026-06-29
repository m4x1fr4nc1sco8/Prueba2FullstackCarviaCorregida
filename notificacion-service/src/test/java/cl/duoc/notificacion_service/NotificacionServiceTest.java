package cl.duoc.notificacion_service;

import cl.duoc.notificacion_service.exception.NotificacionNotExistException;
import cl.duoc.notificacion_service.model.Notificacion;
import cl.duoc.notificacion_service.repository.NotificacionRepository;
import cl.duoc.notificacion_service.service.NotificacionService;
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
@DisplayName("Pruebas unitarias para NotificacionService")
public class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    private Notificacion notificacion;

    @BeforeEach
    void setUp() {
        notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setUsuarioId(5L);
        notificacion.setMensaje("Tu vehículo está listo para retiro");
        notificacion.setTipoNotificacion("Informativa");
        notificacion.setFechaEnvio(LocalDate.now());
        notificacion.setEstadoNotificacion("Enviada");
    }

    @Test
    @DisplayName("Debe listar todas las notificaciones correctamente")
    void listarTodos_deberiaRetornarListaDeNotificaciones() {
        when(notificacionRepository.findAll()).thenReturn(Arrays.asList(notificacion));

        List<Notificacion> resultado = notificacionService.obtenerNotificaciones();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(notificacionRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar una notificación por ID cuando existe")
    void buscarPorId_cuandoExiste_deberiaRetornarNotificacion() {
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacion));

        Notificacion resultado = notificacionService.buscarNotificacionPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Tu vehículo está listo para retiro", resultado.getMensaje());
        verify(notificacionRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar NotificacionNotExistException cuando la notificación no existe")
    void buscarPorId_cuandoNoExiste_deberiaLanzarNotificacionNotExistException() {
        // Simulamos base de datos vacía
        when(notificacionRepository.findById(99L)).thenReturn(Optional.empty());

        // Comprobamos la llamada exacta usando el assertThrows idéntico al del profesor
        NotificacionNotExistException exception = assertThrows(
                NotificacionNotExistException.class,
                () -> notificacionService.buscarNotificacionPorId(99L)
        );

        assertEquals("Notificación no encontrada", exception.getMessage());
        verify(notificacionRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar una notificación correctamente")
    void guardar_deberiaRetornarNotificacionGuardada() {
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacion);

        Notificacion resultado = notificacionService.guardarNotificacion(new Notificacion());

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(notificacionRepository).save(any(Notificacion.class));
    }

    @Test
    @DisplayName("Debe eliminar una notificación por ID correctamente")
    void eliminar_deberiaEjecutarEliminacion() {

        doNothing().when(notificacionRepository).deleteById(1L);

        // 2. Ejecutar el método del servicio (que retorna String)
        String resultado = notificacionService.eliminarNotificacion(1L);

        assertNotNull(resultado);
        verify(notificacionRepository).deleteById(1L);
    }
}