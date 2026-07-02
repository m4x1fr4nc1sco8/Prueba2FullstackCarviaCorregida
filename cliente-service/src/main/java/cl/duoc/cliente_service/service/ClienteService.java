package cl.duoc.cliente_service.service;

import cl.duoc.cliente_service.model.Cliente;
import cl.duoc.cliente_service.repository.ClienteRepository;
import cl.duoc.cliente_service.feign.PagoFeignClient;
import cl.duoc.cliente_service.feign.NotificacionFeignClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PagoFeignClient pagoFeignClient;
    private final NotificacionFeignClient notificacionFeignClient;

    public ClienteService(ClienteRepository clienteRepository,
                          PagoFeignClient pagoFeignClient,
                          NotificacionFeignClient notificacionFeignClient) {
        this.clienteRepository = clienteRepository;
        this.pagoFeignClient = pagoFeignClient;
        this.notificacionFeignClient = notificacionFeignClient;
    }

    public List<Cliente> obtenerClientes() {
        List<Cliente> clientes = clienteRepository.findAll();

        for (Cliente cliente : clientes) {

            cliente.setPagos(new ArrayList<>());
            cliente.setNotificaciones(new ArrayList<>());


            try {
                var pagos = pagoFeignClient.obtenerPagosPorClienteId(cliente.getId());
                if (pagos != null) {
                    cliente.setPagos(pagos);
                }
            } catch (Exception e) {

            }

            try {
                var notificaciones = notificacionFeignClient.obtenerNotificacionesPorUsuarioId(cliente.getId());
                if (notificaciones != null) {
                    cliente.setNotificaciones(notificaciones);
                }
            } catch (Exception e) {
                // Si falla el microservicio, se mantiene el arreglo vacío []
            }
        }

        return clientes;
    }

    public Optional<Cliente> obtenerClientePorId(Long id) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();

            try {
                cliente.setPagos(pagoFeignClient.obtenerPagosPorClienteId(id));
            } catch (Exception e) {
                cliente.setPagos(List.of());
            }

            try {
                cliente.setNotificaciones(notificacionFeignClient.obtenerNotificacionesPorUsuarioId(id));
            } catch (Exception e) {
                cliente.setNotificaciones(List.of());
            }
        }

        return clienteOpt;
    }

    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

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

    public List<Cliente> buscarPorApellido(String apellido) {
        return clienteRepository.findByApellidoContainingIgnoreCase(apellido);
    }
}