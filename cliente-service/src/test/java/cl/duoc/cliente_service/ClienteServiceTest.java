package cl.duoc.cliente_service;

import cl.duoc.cliente_service.exception.ClienteNotExistException;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para ClienteService")
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente(
                1L,
                "Juan",
                "Pérez",
                "juan.perez@mail.com",
                "clave1234",
                "+56912345678",
                List.of(),
                List.of()
        );
    }

    @Test
    @DisplayName("Debe retornar un Optional vacío cuando el cliente no existe")
    public void buscarPorId_cuandoNoExiste_deberiaRetornarOptionalVacio() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Cliente> resultado = clienteService.obtenerClientePorId(99L);

        assertFalse(resultado.isPresent());

        verify(clienteRepository).findById(99L);
    }

    @Test
    @DisplayName("Debe listar todos los clientes correctamente")
    public void listarTodos_deberiaRetornarListaDeClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<Cliente> resultado = clienteService.obtenerClientes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());

        verify(clienteRepository).findAll();
    }

    @Test
    @DisplayName("Debe guardar un cliente correctamente")
    public void guardar_deberiaGuardarYRetornarCliente() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente resultado = clienteService.guardarCliente(cliente);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan", resultado.getNombre());

        verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("Debe buscar un cliente por ID cuando existe")
    public void buscarPorId_cuandoExiste_deberiaRetornarCliente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultadoOpt = clienteService.obtenerClientePorId(1L);

        assertTrue(resultadoOpt.isPresent());
        Cliente resultado = resultadoOpt.get();
        assertEquals(1L, resultado.getId());
        assertEquals("Juan", resultado.getNombre());

        verify(clienteRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe eliminar un cliente por ID correctamente")
    public void eliminar_deberiaEliminarClientePorId() {
        // CORREGIDO: Llamada al método real de tu servicio
        clienteService.eliminarCliente(1L);

        verify(clienteRepository).deleteById(1L);
    }
}