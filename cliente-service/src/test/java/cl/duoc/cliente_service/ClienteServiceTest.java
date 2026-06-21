package cl.duoc.cliente_service;

import cl.duoc.cliente_service.model.Cliente;
import cl.duoc.cliente_service.repository.ClienteRepository;
import cl.duoc.cliente_service.service.ClienteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para ClienteService")
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

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

    }

    @Test
    @DisplayName("Debe listar todos los clientes")
    void obtenerClientes_deberiaRetornarLista() {

        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<Cliente> resultado = clienteService.obtenerClientes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Maximiliano", resultado.get(0).getNombre());

        verify(clienteRepository).findAll();
    }

    @Test
    @DisplayName("Debe buscar cliente por ID")
    void obtenerClientePorId_deberiaRetornarCliente() {

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.obtenerClientePorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Maximiliano", resultado.get().getNombre());

        verify(clienteRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe guardar un cliente")
    void guardarCliente_deberiaGuardarCorrectamente() {

        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente resultado = clienteService.guardarCliente(cliente);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Maximiliano", resultado.getNombre());

        verify(clienteRepository).save(cliente);
    }

}