package cl.duoc.reportes_service;

import cl.duoc.reportes_service.dto.ClienteDTO;
import cl.duoc.reportes_service.dto.VehiculoDTO;
import cl.duoc.reportes_service.feign.ClienteFeignClient;
import cl.duoc.reportes_service.feign.VehiculoFeignClient;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
@DisplayName("Pruebas Robustas en la capa Controller de Reportes")
public class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteFeignClient clienteFeignClient;

    @MockitoBean
    private VehiculoFeignClient vehiculoFeignClient;

    private ClienteDTO clienteMock;
    private VehiculoDTO vehiculoMock;

    @BeforeEach
    void setUp() {
        clienteMock = new ClienteDTO();
        clienteMock.setId(1L);
        clienteMock.setNombre("Maximiliano");
        clienteMock.setApellido("Gómez");
        clienteMock.setEmail("maxi@mail.com");
        clienteMock.setTelefono("912345678");

        vehiculoMock = new VehiculoDTO();
        vehiculoMock.setId(1L);
        vehiculoMock.setPatente("ABCD12");
        vehiculoMock.setMarca("Toyota");
        vehiculoMock.setModelo("Yaris");
        vehiculoMock.setAnio(2022);
        vehiculoMock.setColor("Rojo");
        vehiculoMock.setTipoVehiculo("Sedan");
    }

    @Test
    @DisplayName("GET /api/v1/reportes/clientes - Debería retornar 200 OK con el listado")
    void testObtenerClientesReporte_Exitoso() throws Exception {
        when(clienteFeignClient.obtenerClientes()).thenReturn(Arrays.asList(clienteMock));

        mockMvc.perform(get("/api/v1/reportes/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Maximiliano"));
    }

    @Test
    @DisplayName("GET /api/v1/reportes/clientes (Vacío) - Debería retornar 200 OK con lista vacía")
    void testObtenerClientesReporte_Vacio() throws Exception {
        when(clienteFeignClient.obtenerClientes()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/reportes/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("GET /api/v1/reportes/vehiculos - Debería retornar 200 OK con el listado")
    void testObtenerVehiculosReporte_Exitoso() throws Exception {
        when(vehiculoFeignClient.obtenerVehiculos()).thenReturn(Arrays.asList(vehiculoMock));

        mockMvc.perform(get("/api/v1/reportes/vehiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].patente").value("ABCD12"));
    }

    @Test
    @DisplayName("GET /api/v1/reportes/vehiculos (Vacío) - Debería retornar 200 OK con lista vacía")
    void testObtenerVehiculosReporte_Vacio() throws Exception {
        when(vehiculoFeignClient.obtenerVehiculos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/reportes/vehiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("GET /api/v1/reportes/vehiculos (Error Feign) - Debería retornar 500 si falla la comunicación")
    void testObtenerVehiculosReporte_ErrorServicio() throws Exception {
        // Simulamos que el cliente Feign lanza una excepción porque el microservicio destino está caído
        when(vehiculoFeignClient.obtenerVehiculos()).thenThrow(FeignException.InternalServerError.class);

        mockMvc.perform(get("/api/v1/reportes/vehiculos"))
                .andExpect(status().isInternalServerError());
    }
}