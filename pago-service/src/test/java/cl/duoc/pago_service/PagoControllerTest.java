package cl.duoc.pago_service;

import cl.duoc.pago_service.controller.PagoController;
import cl.duoc.pago_service.model.Pago;
import cl.duoc.pago_service.service.PagoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(PagoController.class)
public class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagoService pagoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pago pagoPrueba;

    @BeforeEach
    void setUp() {
        pagoPrueba = new Pago(
                1L,
                150L,
                50L,
                125000.0,
                LocalDate.of(2026, 6, 21),
                "Tarjeta de Crédito",
                "COMPLETADO"
        );
    }



    @Test
    @DisplayName("POST /api/v1/pagos - Debe crear un pago (HTTP 201)")
    void crearPago_DeberiaRetornarStatusCreated() throws Exception {
        when(pagoService.guardarPago(any(Pago.class))).thenReturn(pagoPrueba);

        mockMvc.perform(post("/api/v1/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pagoPrueba)))
                // status().isCreated() porque en el Controller usaste @ResponseStatus(HttpStatus.CREATED)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.metodoPago").value("Tarjeta de Crédito"));
    }

    @Test
    @DisplayName("DELETE /api/v1/pagos/{id} - Debe eliminar el pago (HTTP 204)")
    void eliminarPago_DeberiaRetornarStatusNoContent() throws Exception {
        String mensajeEsperado = "Pago eliminado correctamente";
        when(pagoService.eliminarPago(1L)).thenReturn(mensajeEsperado);

        mockMvc.perform(delete("/api/v1/pagos/{id}", 1L))
                // status().isNoContent() porque usaste @ResponseStatus(HttpStatus.NO_CONTENT)
                .andExpect(status().isNoContent())
                .andExpect(content().string(mensajeEsperado));
    }
}