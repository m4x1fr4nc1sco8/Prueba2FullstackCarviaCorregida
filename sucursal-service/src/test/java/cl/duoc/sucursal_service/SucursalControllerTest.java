package cl.duoc.sucursal_service;

import cl.duoc.sucursal_service.controller.SucursalController;
import cl.duoc.sucursal_service.model.Sucursal;
import cl.duoc.sucursal_service.service.SucursalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(SucursalController.class)
public class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SucursalService sucursalService;

    @Autowired
    private ObjectMapper objectMapper;

    private Sucursal sucursalPrueba;

    @BeforeEach
    void setUp() {
        sucursalPrueba = new Sucursal(
                1L,
                "Sucursal Santiago Centro",
                "Av. Libertador Bernardo O'Higgins 123",
                "Santiago",
                "+56912345678",
                "09:00 - 18:00",
                "ACTIVA"
        );
    }

    @Test
    @DisplayName("GET /api/v1/sucursales - Debe listar todas las sucursales")
    void obtenerSucursales_DeberiaRetornarLista() throws Exception {
        List<Sucursal> listaSucursales = Arrays.asList(sucursalPrueba);
        when(sucursalService.obtenerSucursales()).thenReturn(listaSucursales);

        mockMvc.perform(get("/api/v1/sucursales")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombreSucursal").value("Sucursal Santiago Centro"))
                .andExpect(jsonPath("$[0].ciudad").value("Santiago"));
    }

    @Test
    @DisplayName("GET /api/v1/sucursales/{id} - Debe obtener sucursal por ID")
    void obtenerSucursalPorId_DeberiaRetornarSucursal() throws Exception {
        when(sucursalService.buscarSucursalPorId(1L)).thenReturn(sucursalPrueba);

        mockMvc.perform(get("/api/v1/sucursales/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.telefono").value("+56912345678"))
                .andExpect(jsonPath("$.estadoSucursal").value("ACTIVA"));
    }

}