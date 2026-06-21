package cl.duoc.mantencion_service;

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
public class MantencionServiceTest {

    @Mock
    private MantencionRepository mantencionRepository;

    @InjectMocks
    private MantencionService mantencionService;

    private Mantencion mantencionPrueba;

    @BeforeEach
    void setUp() {
        // Inicializamos el objeto que usaremos en los escenarios de prueba
        mantencionPrueba = new Mantencion(
                1L,
                100L,
                "Cambio de pastillas de freno",
                LocalDate.of(2026, 6, 21),
                85000.0,
                "Correctiva",
                "Pendiente"
        );
    }

    @Test
    @DisplayName("Debería retornar todas las mantenciones desde el repositorio")
    void obtenerMantenciones_DeberiaRetornarListaCompleta() {
        // Arrange (Configurar datos y comportamiento del Mock)
        when(mantencionRepository.findAll()).thenReturn(Arrays.asList(mantencionPrueba));

        // Act (Ejecutar el método del servicio)
        List<Mantencion> resultado = mantencionService.obtenerMantenciones();

        // Assert (Verificar el resultado)
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Cambio de pastillas de freno", resultado.get(0).getDescripcion());
        verify(mantencionRepository, times(1)).findAll(); // Asegura que llamó al repositorio exactamente 1 vez
    }


    @Test
    @DisplayName("Debería guardar y retornar la mantención correctamente")
    void guardarMantencion_DeberiaGuardarExitosamente() {
        // Arrange
        when(mantencionRepository.save(any(Mantencion.class))).thenReturn(mantencionPrueba);

        // Act
        Mantencion resultado = mantencionService.guardarMantencion(mantencionPrueba);

        // Assert
        assertNotNull(resultado);
        assertEquals("Pendiente", resultado.getEstadoMantencion());
        verify(mantencionRepository, times(1)).save(mantencionPrueba);
    }

    @Test
    @DisplayName("Debería eliminar la mantención por su ID y devolver mensaje de éxito")
    void eliminarMantencion_DeberiaLlamarAlRepositoryYDevolverMensaje() {
        // En tu servicio el método deleteById es void, por lo que no usamos 'when()' para él.
        // Solo llamamos al método y comprobamos el string de retorno.

        // Act
        String mensajeResultado = mantencionService.eliminarMantencion(1L);

        // Assert
        assertEquals("Mantención eliminada correctamente", mensajeResultado);
        verify(mantencionRepository, times(1)).deleteById(1L); // Crucial: Verifica que la eliminación sí se ordenó a la BD
    }
}