package cl.duoc.mantencion_service;

import cl.duoc.mantencion_service.controller.MantencionController;
import cl.duoc.mantencion_service.model.Mantencion;
import cl.duoc.mantencion_service.service.MantencionService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(MantencionController.class)
@DisplayName("Pruebas de MantencionController")
class MantencionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MantencionService mantencionService;

    @Autowired
    private ObjectMapper objectMapper;

    private Mantencion mantencion;

    @BeforeEach
    void setUp() {

        mantencion = new Mantencion(
                1L,
                100L,
                "Cambio de aceite",
                LocalDate.of(2025, 6, 20),
                45000.0,
                "Preventiva",
                "Pendiente"
        );

    }

    @Test
    @DisplayName("GET /api/v1/mantenciones")
    void obtenerMantenciones() throws Exception {

        doReturn(List.of(mantencion))
                .when(mantencionService)
                .obtenerMantenciones();

        mockMvc.perform(get("/api/v1/mantenciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].vehiculoId").value(100))
                .andExpect(jsonPath("$[0].descripcion").value("Cambio de aceite"))
                .andExpect(jsonPath("$[0].costo").value(45000.0));
    }


    @Test
    @DisplayName("DELETE /api/v1/mantenciones/{id}")
    void eliminarMantencion() throws Exception {

        doReturn("Mantención eliminada correctamente")
                .when(mantencionService)
                .eliminarMantencion(1L);

        mockMvc.perform(delete("/api/v1/mantenciones/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Mantención eliminada correctamente"));
    }

}