package cl.duoc.cliente_service.controller;

import cl.duoc.cliente_service.model.Cliente;
import cl.duoc.cliente_service.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // OBTENER TODOS LOS CLIENTES
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.obtenerClientes();
    }

    // OBTENER CLIENTE POR ID
    @GetMapping("/{id}")
    public Optional<Cliente> obtenerCliente(@PathVariable Long id) {
        return clienteService.obtenerClientePorId(id);
    }

    // CREAR CLIENTE
    @PostMapping
    public Cliente guardarCliente(@RequestBody Cliente cliente) {
        return clienteService.guardarCliente(cliente);
    }

    // ACTUALIZAR CLIENTE
    @PutMapping("/{id}")
    public Cliente actualizarCliente(@PathVariable Long id,
                                     @RequestBody Cliente cliente) {
        return clienteService.actualizarCliente(id, cliente);
    }

    // ELIMINAR CLIENTE
    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
    }
}