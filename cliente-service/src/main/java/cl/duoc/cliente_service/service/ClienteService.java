package cl.duoc.cliente_service.service;

import cl.duoc.cliente_service.model.Cliente;
import cl.duoc.cliente_service.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // LISTAR TODOS LOS CLIENTES
    public List<Cliente> obtenerClientes() {
        return clienteRepository.findAll();
    }

    // BUSCAR CLIENTE POR ID
    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    // GUARDAR CLIENTE
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // ELIMINAR CLIENTE
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    // ACTUALIZAR CLIENTE
    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {

        Cliente cliente = clienteRepository.findById(id).orElse(null);

        if (cliente != null) {

            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setApellido(clienteActualizado.getApellido());
            cliente.setEmail(clienteActualizado.getEmail());
            cliente.setContrasenia(clienteActualizado.getContrasenia());
            cliente.setTelefono(clienteActualizado.getTelefono());

            return clienteRepository.save(cliente);
        }

        return null;
    }
}