package cl.duoc.cliente_service;

import cl.duoc.cliente_service.controller.ClienteController;
import cl.duoc.cliente_service.model.Cliente;
import cl.duoc.cliente_service.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(ClienteController.class)
@DisplayName("Pruebas en la capa Controller de clientes")
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    private Cliente cliente;
    private Cliente clienteSinId;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "Juan", "Pérez", "juan.perez@mail.com", "clave1234", "+56912345678", List.of(), List.of(), List.of());
        clienteSinId = new Cliente(null, "Juan", "Pérez", "juan.perez@mail.com", "clave1234", "+56912345678", List.of(), List.of(), List.of());
    }

    @Test
    @DisplayName("GET /api/v1/clientes - Debería retornar 200 OK y la lista de clientes")
    public void testEndpointListarTodos() throws Exception {
        when(clienteService.obtenerClientes()).thenReturn(List.of(cliente));

        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].apellido").value("Pérez"))
                .andExpect(jsonPath("$[0].email").value("juan.perez@mail.com"));
    }

    @Test
    @DisplayName("POST /api/v1/clientes - Debería retornar 201 CREATED y el cliente creado")
    void testEndpointCrear() throws Exception {

        when(clienteService.guardarCliente(any(Cliente.class))).thenReturn(cliente);
        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteSinId)))
                .andExpect(status().isCreated()) // <-- CAMBIADO A isCreated() (201)
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    @DisplayName("GET /api/v1/clientes/{id} - Debería retornar 200 OK y el cliente encontrado")
    void testEndpointBuscarPorId() throws Exception {

        when(clienteService.obtenerClientePorId(1L)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/api/v1/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }
    @Test
    @DisplayName("DELETE /api/v1/clientes/{id} - Debería retornar 204 NO CONTENT")
    void testEndpointEliminar() throws Exception {

        org.mockito.Mockito.doNothing().when(clienteService).eliminarCliente(1L);
        mockMvc.perform(delete("/api/v1/clientes/1"))
                .andExpect(status().isNoContent());
    }

}