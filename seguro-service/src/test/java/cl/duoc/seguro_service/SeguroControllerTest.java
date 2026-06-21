package cl.duoc.seguro_service;

import cl.duoc.seguro_service.controller.SeguroController;
import cl.duoc.seguro_service.model.Seguro;
import cl.duoc.seguro_service.service.SeguroService;
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

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(SeguroController.class)
public class SeguroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SeguroService seguroService;

    @Autowired
    private ObjectMapper objectMapper;

    private Seguro seguroPrueba;

    @BeforeEach
    void setUp() {
        seguroPrueba = new Seguro(
                1L,
                100L,
                "Seguro Automotriz Pro",
                "Cobertura Total",
                45000.0,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2026, 1, 1),
                "ACTIVO"
        );
    }


    @Test
    @DisplayName("POST /api/v1/seguros - Debe crear un seguro (HTTP 201)")
    void crearSeguro_DeberiaRetornarStatusCreated() throws Exception {
        when(seguroService.guardarSeguro(any(Seguro.class))).thenReturn(seguroPrueba);

        mockMvc.perform(post("/api/v1/seguros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seguroPrueba)))
                // status().isCreated() dado el @ResponseStatus(HttpStatus.CREATED) del controller
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.tipoCobertura").value("Cobertura Total"));
    }

    @Test
    @DisplayName("DELETE /api/v1/seguros/{id} - Debe eliminar el seguro (HTTP 204)")
    void eliminarSeguro_DeberiaRetornarStatusNoContent() throws Exception {
        String mensajeEsperado = "Seguro eliminado correctamente";
        when(seguroService.eliminarSeguro(1L)).thenReturn(mensajeEsperado);

        mockMvc.perform(delete("/api/v1/seguros/{id}", 1L))
                // status().isNoContent() dado el @ResponseStatus(HttpStatus.NO_CONTENT) del controller
                .andExpect(status().isNoContent())
                .andExpect(content().string(mensajeEsperado));
    }
}