package cl.duoc.seguro_service;

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
public class SeguroServiceTest {

    @Mock
    private SeguroRepository seguroRepository;

    @InjectMocks
    private SeguroService seguroService;

    private Seguro seguroPrueba;

    @BeforeEach
    void setUp() {
        // Inicializamos el objeto Seguro basándonos en tu modelo
        seguroPrueba = new Seguro(
                1L,
                100L, // vehiculoId
                "Seguro Automotriz Pro",
                "Cobertura Total",
                45000.0,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2026, 1, 1),
                "ACTIVO"
        );
    }

    @Test
    @DisplayName("Debería retornar todos los seguros")
    void obtenerSeguros_DeberiaRetornarListaCompleta() {
        when(seguroRepository.findAll()).thenReturn(Arrays.asList(seguroPrueba));

        List<Seguro> resultado = seguroService.obtenerSeguros();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Seguro Automotriz Pro", resultado.get(0).getNombreSeguro());
        verify(seguroRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("Debería guardar y retornar el seguro correctamente")
    void guardarSeguro_DeberiaGuardarExitosamente() {
        when(seguroRepository.save(any(Seguro.class))).thenReturn(seguroPrueba);

        Seguro resultado = seguroService.guardarSeguro(seguroPrueba);

        assertNotNull(resultado);
        assertEquals("ACTIVO", resultado.getEstadoSeguro());
        verify(seguroRepository, times(1)).save(seguroPrueba);
    }

    @Test
    @DisplayName("Debería eliminar el seguro y devolver mensaje de éxito")
    void eliminarSeguro_DeberiaLlamarAlRepositoryYDevolverMensaje() {
        String mensajeResultado = seguroService.eliminarSeguro(1L);

        assertEquals("Seguro eliminado correctamente", mensajeResultado);
        verify(seguroRepository, times(1)).deleteById(1L);
    }
}