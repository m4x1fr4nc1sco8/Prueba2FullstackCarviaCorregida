package cl.duoc.sucursal_service;

import cl.duoc.sucursal_service.controller.SucursalController;
import cl.duoc.sucursal_service.exception.SucursalNotExistException;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(SucursalController.class)
@DisplayName("Pruebas en la capa Controller de Sucursales")
public class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SucursalService sucursalService;

    @Autowired
    private ObjectMapper objectMapper;

    private Sucursal sucursal;
    private Sucursal sucursalSinId;

    @BeforeEach
    void setUp() {
        sucursal = new Sucursal();
        sucursal.setId(1L);
        sucursal.setNombreSucursal("Sucursal Plaza Vespucio");
        sucursal.setDireccion("Av. Vicuña Mackenna 7110");
        sucursal.setCiudad("Santiago");
        sucursal.setTelefono("+56912345678");
        sucursal.setHorarioAtencion("09:00 a 18:00");
        sucursal.setEstadoSucursal("Activa");

        sucursalSinId = new Sucursal();
        sucursalSinId.setNombreSucursal("Sucursal Plaza Vespucio");
        sucursalSinId.setDireccion("Av. Vicuña Mackenna 7110");
        sucursalSinId.setCiudad("Santiago");
        sucursalSinId.setTelefono("+56912345678");
        sucursalSinId.setHorarioAtencion("09:00 a 18:00");
        sucursalSinId.setEstadoSucursal("Activa");
    }

    @Test
    @DisplayName("GET /api/v1/sucursales - Debería retornar 200 OK con la lista")
    void testEndpointListar() throws Exception {
        when(sucursalService.obtenerSucursales()).thenReturn(Arrays.asList(sucursal));

        mockMvc.perform(get("/api/v1/sucursales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombreSucursal").value("Sucursal Plaza Vespucio"));
    }

    @Test
    @DisplayName("GET /api/v1/sucursales/{id} - Debería retornar 200 OK cuando existe")
    void testEndpointBuscarPorId_cuandoExiste() throws Exception {
        when(sucursalService.buscarSucursalPorId(1L)).thenReturn(sucursal);

        mockMvc.perform(get("/api/v1/sucursales/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreSucursal").value("Sucursal Plaza Vespucio"));
    }

    @Test
    @DisplayName("GET /api/v1/sucursales/{id} - Debería retornar 404 NOT FOUND si no existe")
    void testEndpointBuscarPorId_cuandoNoExiste() throws Exception {
        when(sucursalService.buscarSucursalPorId(99L))
                .thenThrow(new SucursalNotExistException("Sucursal no encontrada"));

        mockMvc.perform(get("/api/v1/sucursales/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/sucursales - Debería retornar 201 CREATED")
    void testEndpointCrear() throws Exception {
        when(sucursalService.guardarSucursal(any(Sucursal.class))).thenReturn(sucursal);

        mockMvc.perform(post("/api/v1/sucursales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sucursalSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("DELETE /api/v1/sucursales/{id} - Debería retornar 204 NO CONTENT")
    void testEndpointEliminar() throws Exception {
        when(sucursalService.eliminarSucursal(1L)).thenReturn("Sucursal eliminada correctamente");

        mockMvc.perform(delete("/api/v1/sucursales/1"))
                .andExpect(status().isNoContent());
    }
}