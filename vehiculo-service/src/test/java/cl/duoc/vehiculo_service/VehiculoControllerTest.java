package cl.duoc.vehiculo_service;

import cl.duoc.vehiculo_service.model.Vehiculo;
import cl.duoc.vehiculo_service.service.VehiculoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
@DisplayName("Pruebas en la capa Controller de Vehículos")
public class VehiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VehiculoService vehiculoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Vehiculo vehiculo;
    private Vehiculo vehiculoSinId;

    @BeforeEach
    void setUp() {
        vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setPatente("ABCD12");
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Yaris");
        vehiculo.setAnio(2022);
        vehiculo.setColor("Rojo");
        vehiculo.setSucursalId(2L);
        vehiculo.setTipovehiculo("Sedan");

        vehiculoSinId = new Vehiculo();
        vehiculoSinId.setPatente("ABCD12");
        vehiculoSinId.setMarca("Toyota");
        vehiculoSinId.setModelo("Yaris");
        vehiculoSinId.setAnio(2022);
        vehiculoSinId.setColor("Rojo");
        vehiculoSinId.setSucursalId(2L);
        vehiculoSinId.setTipovehiculo("Sedan");
    }

    @Test
    @DisplayName("GET /api/v1/vehiculos - Debería retornar 200 OK con el listado")
    void testEndpointListar() throws Exception {
        when(vehiculoService.obtenerVehiculos()).thenReturn(Arrays.asList(vehiculo));

        mockMvc.perform(get("/api/v1/vehiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].patente").value("ABCD12"));
    }

    @Test
    @DisplayName("GET /api/v1/vehiculos/{id} - Debería retornar 200 OK cuando existe")
    void testEndpointBuscarPorId_cuandoExiste() throws Exception {
        when(vehiculoService.obtenerVehiculoPorId(1L)).thenReturn(Optional.of(vehiculo));

        mockMvc.perform(get("/api/v1/vehiculos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.patente").value("ABCD12"));
    }

    @Test
    @DisplayName("GET /api/v1/vehiculos/{id} - Debería retornar 404 NOT FOUND si no existe")
    void testEndpointBuscarPorId_cuandoNoExiste() throws Exception {
        when(vehiculoService.obtenerVehiculoPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/vehiculos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/vehiculos - Debería retornar 201 CREATED")
    void testEndpointCrear() throws Exception {
        when(vehiculoService.guardarVehiculo(any(Vehiculo.class))).thenReturn(vehiculo);

        mockMvc.perform(post("/api/v1/vehiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehiculoSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }


    @Test
    @DisplayName("DELETE /api/v1/vehiculos/{id} - Debería retornar 204 NO CONTENT")
    void testEndpointEliminar() throws Exception {
        doNothing().when(vehiculoService).eliminarVehiculo(1L);

        mockMvc.perform(delete("/api/v1/vehiculos/1"))
                .andExpect(status().isNoContent());
    }
}