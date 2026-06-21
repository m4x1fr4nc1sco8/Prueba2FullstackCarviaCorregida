package cl.duoc.reportes_service;

import cl.duoc.reportes_service.controller.ReporteController;
import cl.duoc.reportes_service.dto.ClienteDTO;
import cl.duoc.reportes_service.dto.VehiculoDTO;
import cl.duoc.reportes_service.feign.ClienteFeignClient;
import cl.duoc.reportes_service.feign.VehiculoFeignClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(ReporteController.class)
public class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mockeamos directamente los clientes Feign
    @MockitoBean
    private ClienteFeignClient clienteFeignClient;

    @MockitoBean
    private VehiculoFeignClient vehiculoFeignClient;

    @Test
    @DisplayName("GET /api/v1/reportes/clientes - Debe listar clientes desde Feign")
    void obtenerClientes_DeberiaRetornarLista() throws Exception {
        // Creamos un DTO vacío para no pelear con setters
        ClienteDTO clienteMock = new ClienteDTO();

        // Le decimos al test que cuando el Feign intente buscar, devuelva nuestra lista
        when(clienteFeignClient.obtenerClientes()).thenReturn(Arrays.asList(clienteMock));

        mockMvc.perform(get("/api/v1/reportes/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1)); // Validamos que traiga 1 elemento
    }

    @Test
    @DisplayName("GET /api/v1/reportes/vehiculos - Debe listar vehículos desde Feign")
    void obtenerVehiculos_DeberiaRetornarLista() throws Exception {
        // Creamos un DTO vacío
        VehiculoDTO vehiculoMock = new VehiculoDTO();

        when(vehiculoFeignClient.obtenerVehiculos()).thenReturn(Arrays.asList(vehiculoMock));

        mockMvc.perform(get("/api/v1/reportes/vehiculos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1)); // Validamos que traiga 1 elemento
    }
}