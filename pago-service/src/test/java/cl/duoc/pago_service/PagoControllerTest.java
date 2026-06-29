package cl.duoc.pago_service;

import cl.duoc.pago_service.controller.PagoController;
import cl.duoc.pago_service.exception.PagoNotExistException;
import cl.duoc.pago_service.model.Pago;
import cl.duoc.pago_service.service.PagoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(PagoController.class)
@DisplayName("Pruebas en la capa Controller de Pagos")
public class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagoService pagoService;

    private ObjectMapper objectMapper;
    private Pago pago;
    private Pago pagoSinId;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        pago = new Pago();
        pago.setId(1L);
        pago.setReservaId(10L);
        pago.setClienteId(5L);
        pago.setMonto(45000.0);
        pago.setFechaPago(LocalDate.now());
        pago.setMetodoPago("Tarjeta de Credito");
        pago.setEstadoPago("Aprobado");

        pagoSinId = new Pago();
        pagoSinId.setReservaId(10L);
        pagoSinId.setClienteId(5L);
        pagoSinId.setMonto(45000.0);
        pagoSinId.setFechaPago(LocalDate.now());
        pagoSinId.setMetodoPago("Tarjeta de Credito");
        pagoSinId.setEstadoPago("Aprobado");
    }

    @Test
    @DisplayName("GET /api/v1/pagos - Debería retornar 200 OK con la lista")
    void testEndpointListar() throws Exception {
        when(pagoService.obtenerPagos()).thenReturn(Arrays.asList(pago));

        mockMvc.perform(get("/api/v1/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].metodoPago").value("Tarjeta de Credito"));
    }

    @Test
    @DisplayName("GET /api/v1/pagos/{id} - Debería retornar 200 OK cuando existe")
    void testEndpointBuscarPorId_cuandoExiste() throws Exception {
        when(pagoService.buscarPagoPorId(1L)).thenReturn(pago);

        mockMvc.perform(get("/api/v1/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.metodoPago").value("Tarjeta de Credito"));
    }

    @Test
    @DisplayName("GET /api/v1/pagos/{id} - Debería retornar 404 NOT FOUND si no existe")
    void testEndpointBuscarPorId_cuandoNoExiste() throws Exception {
        when(pagoService.buscarPagoPorId(99L))
                .thenThrow(new PagoNotExistException("Pago no encontrado"));

        mockMvc.perform(get("/api/v1/pagos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/pagos - Debería retornar 201 CREATED")
    void testEndpointCrear() throws Exception {
        when(pagoService.guardarPago(any(Pago.class))).thenReturn(pago);

        mockMvc.perform(post("/api/v1/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pagoSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("DELETE /api/v1/pagos/{id} - Debería retornar 204 NO CONTENT")
    void testEndpointEliminar() throws Exception {
        when(pagoService.eliminarPago(1L)).thenReturn("Pago eliminado correctamente");

        mockMvc.perform(delete("/api/v1/pagos/1"))
                .andExpect(status().isNoContent());
    }
}