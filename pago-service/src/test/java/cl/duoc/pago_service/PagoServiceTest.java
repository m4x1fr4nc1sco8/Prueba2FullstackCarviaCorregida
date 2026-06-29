package cl.duoc.pago_service;

import cl.duoc.pago_service.exception.PagoNotExistException;
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
@DisplayName("Pruebas unitarias para PagoService")
public class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoService pagoService;

    private Pago pago;

    @BeforeEach
    void setUp() {
        pago = new Pago();
        pago.setId(1L);
        pago.setReservaId(10L);
        pago.setClienteId(5L);
        pago.setMonto(45000.0);
        pago.setFechaPago(LocalDate.now());
        pago.setMetodoPago("Tarjeta de Credito");
        pago.setEstadoPago("Aprobado");
    }

    @Test
    @DisplayName("Debe listar todos los pagos correctamente")
    void listarTodos_deberiaRetornarListaDePagos() {
        when(pagoRepository.findAll()).thenReturn(Arrays.asList(pago));

        List<Pago> resultado = pagoService.obtenerPagos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(pagoRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar un pago por ID cuando existe")
    void buscarPorId_cuandoExiste_deberiaRetornarPago() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));

        Pago resultado = pagoService.buscarPagoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Tarjeta de Credito", resultado.getMetodoPago());
        verify(pagoRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar PagoNotExistException cuando el pago no existe")
    void buscarPorId_cuandoNoExiste_deberiaLanzarPagoNotExistException() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        PagoNotExistException exception = assertThrows(
                PagoNotExistException.class,
                () -> pagoService.buscarPagoPorId(99L)
        );

        assertEquals("Pago no encontrado", exception.getMessage());
        verify(pagoRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar un pago correctamente")
    void guardar_deberiaRetornarPagoGuardado() {
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);

        Pago resultado = pagoService.guardarPago(new Pago());

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(pagoRepository).save(any(Pago.class));
    }

    @Test
    @DisplayName("Debe eliminar un pago por ID correctamente")
    void eliminar_deberiaEjecutarEliminacion() {
        doNothing().when(pagoRepository).deleteById(1L);

        String resultado = pagoService.eliminarPago(1L);

        assertNotNull(resultado);
        verify(pagoRepository).deleteById(1L);
    }
}