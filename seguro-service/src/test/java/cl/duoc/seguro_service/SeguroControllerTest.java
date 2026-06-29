package cl.duoc.seguro_service;

import cl.duoc.seguro_service.controller.SeguroController;
import cl.duoc.seguro_service.exception.SeguroNotExistException;
import cl.duoc.seguro_service.model.Seguro;
import cl.duoc.seguro_service.service.SeguroService;
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

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(SeguroController.class)
@DisplayName("Pruebas en la capa Controller de Seguros")
public class SeguroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SeguroService seguroService;

    private ObjectMapper objectMapper;
    private Seguro seguro;
    private Seguro seguroSinId;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        seguro = new Seguro();
        seguro.setId(1L);
        seguro.setVehiculoId(10L);
        seguro.setNombreSeguro("Seguro Total Carvia");
        seguro.setTipoCobertura("Cobertura Completa");
        seguro.setCostoSeguro(35000.0);
        seguro.setFechaInicio(LocalDate.now());
        seguro.setFechaFin(LocalDate.now().plusYears(1));
        seguro.setEstadoSeguro("Activo");

        seguroSinId = new Seguro();
        seguroSinId.setVehiculoId(10L);
        seguroSinId.setNombreSeguro("Seguro Total Carvia");
        seguroSinId.setTipoCobertura("Cobertura Completa");
        seguroSinId.setCostoSeguro(35000.0);
        seguroSinId.setFechaInicio(LocalDate.now());
        seguroSinId.setFechaFin(LocalDate.now().plusYears(1));
        seguroSinId.setEstadoSeguro("Activo");
    }

    @Test
    @DisplayName("GET /api/v1/seguros - Debería retornar 200 OK con el listado")
    void testEndpointListar() throws Exception {
        when(seguroService.obtenerSeguros()).thenReturn(Arrays.asList(seguro));

        mockMvc.perform(get("/api/v1/seguros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombreSeguro").value("Seguro Total Carvia"));
    }

    @Test
    @DisplayName("GET /api/v1/seguros/{id} - Debería retornar 200 OK cuando existe")
    void testEndpointBuscarPorId_cuandoExiste() throws Exception {
        when(seguroService.buscarSeguroPorId(1L)).thenReturn(seguro);

        mockMvc.perform(get("/api/v1/seguros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreSeguro").value("Seguro Total Carvia"));
    }

    @Test
    @DisplayName("GET /api/v1/seguros/{id} - Debería retornar 404 NOT FOUND si no existe")
    void testEndpointBuscarPorId_cuandoNoExiste() throws Exception {
        when(seguroService.buscarSeguroPorId(99L))
                .thenThrow(new SeguroNotExistException("Seguro no encontrado"));

        mockMvc.perform(get("/api/v1/seguros/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/seguros - Debería retornar 201 CREATED")
    void testEndpointCrear() throws Exception {
        when(seguroService.guardarSeguro(any(Seguro.class))).thenReturn(seguro);

        mockMvc.perform(post("/api/v1/seguros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seguroSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("DELETE /api/v1/seguros/{id} - Debería retornar 204 NO CONTENT")
    void testEndpointEliminar() throws Exception {
        when(seguroService.eliminarSeguro(1L)).thenReturn("Seguro eliminado correctamente");

        mockMvc.perform(delete("/api/v1/seguros/1"))
                .andExpect(status().isNoContent());
    }
}