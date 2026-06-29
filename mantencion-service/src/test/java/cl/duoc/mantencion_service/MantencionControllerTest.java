package cl.duoc.mantencion_service;

import cl.duoc.mantencion_service.controller.MantencionController;
import cl.duoc.mantencion_service.exception.MantencionNotExistException;
import cl.duoc.mantencion_service.model.Mantencion;
import cl.duoc.mantencion_service.service.MantencionService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(MantencionController.class)
@DisplayName("Pruebas en la capa Controller de Mantenciones")
public class MantencionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MantencionService mantencionService;

    private ObjectMapper objectMapper;
    private Mantencion mantencion;
    private Mantencion mantencionSinId;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mantencion = new Mantencion();
        mantencion.setId(1L);
        mantencion.setVehiculoId(10L);
        mantencion.setDescripcion("Cambio de aceite");
        mantencion.setFechaMantencion(LocalDate.now());
        mantencion.setCosto(85000.0);
        mantencion.setTipoMantencion("Correctiva");
        mantencion.setEstadoMantencion("Completada");

        mantencionSinId = new Mantencion();
        mantencionSinId.setVehiculoId(10L);
        mantencionSinId.setDescripcion("Cambio de aceite");
        mantencionSinId.setFechaMantencion(LocalDate.now());
        mantencionSinId.setCosto(85000.0);
        mantencionSinId.setTipoMantencion("Correctiva");
        mantencionSinId.setEstadoMantencion("Completada");
    }

    @Test
    @DisplayName("GET /api/v1/mantenciones - Debería retornar 200 OK con la lista")
    void testEndpointListar() throws Exception {
        when(mantencionService.obtenerMantenciones()).thenReturn(Arrays.asList(mantencion));

        mockMvc.perform(get("/api/v1/mantenciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].descripcion").value("Cambio de aceite"));
    }

    @Test
    @DisplayName("GET /api/v1/mantenciones/{id} - Debería retornar 200 OK cuando existe")
    void testEndpointBuscarPorId_cuandoExiste() throws Exception {
        when(mantencionService.buscarMantencionPorId(1L)).thenReturn(mantencion);

        mockMvc.perform(get("/api/v1/mantenciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/v1/mantenciones/{id} - Debería retornar 404 NOT FOUND si no existe")
    void testEndpointBuscarPorId_cuandoNoExiste() throws Exception {

        when(mantencionService.buscarMantencionPorId(99L))
                .thenThrow(new MantencionNotExistException("Mantención no encontrada"));


        mockMvc.perform(get("/api/v1/mantenciones/99"))
                .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("POST /api/v1/mantenciones - Debería retornar 201 CREATED")
    void testEndpointCrear() throws Exception {
        when(mantencionService.guardarMantencion(any(Mantencion.class))).thenReturn(mantencion);

        mockMvc.perform(post("/api/v1/mantenciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mantencionSinId)))
                .andExpect(status().isCreated()) // Valida el 201 exacto
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("DELETE /api/v1/mantenciones/{id} - Debería retornar 204 NO CONTENT")
    void testEndpointEliminar() throws Exception {
        // CAMBIO AQUÍ: Como tu servicio retorna un String, usamos thenReturn
        when(mantencionService.eliminarMantencion(1L)).thenReturn("Mantención eliminada correctamente");

        mockMvc.perform(delete("/api/v1/mantenciones/1"))
                .andExpect(status().isNoContent());
    }
}