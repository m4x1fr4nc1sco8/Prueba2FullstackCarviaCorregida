package cl.duoc.pago_service;

import cl.duoc.pago_service.model.Pago;
import cl.duoc.pago_service.repository.PagoRepository;
import cl.duoc.pago_service.service.PagoService;
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
public class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoService pagoService;

    private Pago pagoPrueba;

    @BeforeEach
    void setUp() {
        // Inicializamos el objeto Pago basándonos en tu modelo
        pagoPrueba = new Pago(
                1L,
                150L, // reservaId
                50L,  // usuarioId
                125000.0, // monto
                LocalDate.of(2026, 6, 21),
                "Tarjeta de Crédito",
                "COMPLETADO"
        );
    }

    @Test
    @DisplayName("Debería retornar todos los pagos")
    void obtenerPagos_DeberiaRetornarListaCompleta() {
        when(pagoRepository.findAll()).thenReturn(Arrays.asList(pagoPrueba));

        List<Pago> resultado = pagoService.obtenerPagos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tarjeta de Crédito", resultado.get(0).getMetodoPago());
        verify(pagoRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("Debería guardar y retornar el pago correctamente")
    void guardarPago_DeberiaGuardarExitosamente() {
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoPrueba);

        Pago resultado = pagoService.guardarPago(pagoPrueba);

        assertNotNull(resultado);
        assertEquals("COMPLETADO", resultado.getEstadoPago());
        verify(pagoRepository, times(1)).save(pagoPrueba);
    }

    @Test
    @DisplayName("Debería eliminar el pago y devolver mensaje de éxito")
    void eliminarPago_DeberiaLlamarAlRepositoryYDevolverMensaje() {
        String mensajeResultado = pagoService.eliminarPago(1L);

        assertEquals("Pago eliminado correctamente", mensajeResultado);
        verify(pagoRepository, times(1)).deleteById(1L);
    }
}