package cl.duoc.notificacion_service;

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
public class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    private Notificacion notificacionPrueba;

    @BeforeEach
    void setUp() {
        // Inicializamos el objeto Notificacion basándonos en tu modelo
        notificacionPrueba = new Notificacion(
                1L,
                50L,
                "Tu vehículo está listo para retiro",
                "EMAIL",
                LocalDate.of(2026, 6, 21),
                "ENVIADA"
        );
    }

    @Test
    @DisplayName("Debería retornar todas las notificaciones")
    void obtenerNotificaciones_DeberiaRetornarListaCompleta() {
        when(notificacionRepository.findAll()).thenReturn(Arrays.asList(notificacionPrueba));

        List<Notificacion> resultado = notificacionService.obtenerNotificaciones();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tu vehículo está listo para retiro", resultado.get(0).getMensaje());
        verify(notificacionRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("Debería guardar y retornar la notificación correctamente")
    void guardarNotificacion_DeberiaGuardarExitosamente() {
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionPrueba);

        Notificacion resultado = notificacionService.guardarNotificacion(notificacionPrueba);

        assertNotNull(resultado);
        assertEquals("ENVIADA", resultado.getEstadoNotificacion());
        verify(notificacionRepository, times(1)).save(notificacionPrueba);
    }

    @Test
    @DisplayName("Debería eliminar la notificación y devolver mensaje de éxito")
    void eliminarNotificacion_DeberiaLlamarAlRepositoryYDevolverMensaje() {
        String mensajeResultado = notificacionService.eliminarNotificacion(1L);

        assertEquals("Notificación eliminada correctamente", mensajeResultado);
        verify(notificacionRepository, times(1)).deleteById(1L);
    }
}