package cl.duoc.vehiculo_service;

import cl.duoc.vehiculo_service.controller.VehiculoController;
import cl.duoc.vehiculo_service.model.Vehiculo;
import cl.duoc.vehiculo_service.service.VehiculoService;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(VehiculoController.class)
public class VehiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VehiculoService vehiculoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Vehiculo vehiculoPrueba;

    @BeforeEach
    void setUp() {
        vehiculoPrueba = new Vehiculo();
        vehiculoPrueba.setId(1L);
        // Omitimos setSucursal y setEstado para evitar que Lombok nos ponga el código en rojo ☢️
        vehiculoPrueba.setMarca("Toyota");
        vehiculoPrueba.setModelo("Yaris");
        vehiculoPrueba.setAnio(2024);
        vehiculoPrueba.setPatente("ABCD12");
    }

    @Test
    @DisplayName("GET /api/v1/vehiculos - Debe listar todos los vehículos")
    void obtenerVehiculos_DeberiaRetornarLista() throws Exception {
        List<Vehiculo> listaVehiculos = Arrays.asList(vehiculoPrueba);
        when(vehiculoService.obtenerVehiculos()).thenReturn(listaVehiculos);

        mockMvc.perform(get("/api/v1/vehiculos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].marca").value("Toyota"))
                .andExpect(jsonPath("$[0].modelo").value("Yaris"));
    }

    @Test
    @DisplayName("GET /api/v1/vehiculos/{id} - Debe obtener vehículo por ID")
    void obtenerVehiculoPorId_DeberiaRetornarVehiculo() throws Exception {
        when(vehiculoService.obtenerVehiculoPorId(1L)).thenReturn(Optional.of(vehiculoPrueba));

        mockMvc.perform(get("/api/v1/vehiculos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.patente").value("ABCD12"));
    }


    @Test
    @DisplayName("PUT /api/v1/vehiculos/{id} - Debe actualizar un vehículo (HTTP 200)")
    void actualizarVehiculo_DeberiaRetornarVehiculoActualizado() throws Exception {
        Vehiculo vehiculoActualizado = new Vehiculo();
        vehiculoActualizado.setId(1L);
        vehiculoActualizado.setMarca("Toyota");
        vehiculoActualizado.setModelo("Corolla");
        vehiculoActualizado.setAnio(2025);
        vehiculoActualizado.setPatente("WXYZ99");

        // Usamos eq(1L) y any() para que no haya conflicto al recibir el JSON
        when(vehiculoService.actualizarVehiculo(eq(1L), any(Vehiculo.class))).thenReturn(vehiculoActualizado);

        mockMvc.perform(put("/api/v1/vehiculos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehiculoActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value("Corolla"))
                .andExpect(jsonPath("$.anio").value(2025));
    }


}