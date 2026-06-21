package cl.duoc.cliente_service;

import cl.duoc.cliente_service.controller.ClienteController;
import cl.duoc.cliente_service.dto.ClienteDTO;
import cl.duoc.cliente_service.model.Cliente;
import cl.duoc.cliente_service.service.ClienteService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(ClienteController.class)
@DisplayName("Pruebas de ClienteController")
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {

        cliente = new Cliente(
                1L,
                "Maximiliano",
                "Araos",
                "max@gmail.com",
                "1234",
                "987654321"
        );

        clienteDTO = new ClienteDTO();

        clienteDTO.setNombre("Maximiliano");
        clienteDTO.setApellido("Araos");
        clienteDTO.setEmail("max@gmail.com");
        clienteDTO.setTelefono("987654321");
    }

    @Test
    @DisplayName("GET /api/v1/clientes")
    void listarClientes() throws Exception {

        when(clienteService.obtenerClientes())
                .thenReturn(List.of(cliente));

        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Maximiliano"))
                .andExpect(jsonPath("$[0].apellido").value("Araos"));
    }

    @Test
    @DisplayName("GET /api/v1/clientes/{id}")
    void obtenerClientePorId() throws Exception {

        when(clienteService.obtenerClientePorId(1L))
                .thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/api/v1/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Maximiliano"))
                .andExpect(jsonPath("$.apellido").value("Araos"));
    }

}