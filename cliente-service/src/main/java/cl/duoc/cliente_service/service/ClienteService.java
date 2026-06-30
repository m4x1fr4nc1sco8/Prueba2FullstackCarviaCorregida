package cl.duoc.cliente_service.service;

import cl.duoc.cliente_service.model.Cliente;
import cl.duoc.cliente_service.repository.ClienteRepository;
import cl.duoc.cliente_service.feign.PagoFeignClient;
import cl.duoc.cliente_service.feign.NotificacionFeignClient; // <-- Corregido con el nombre correcto de tu interfaz
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PagoFeignClient pagoFeignClient;
    private final NotificacionFeignClient notificacionFeignClient; // <-- Modificado con el nombre de clase correcto

    // Constructor limpio e inyectado correctamente
    public ClienteService(ClienteRepository clienteRepository,
                          PagoFeignClient pagoFeignClient,
                          NotificacionFeignClient notificacionFeignClient) {
        this.clienteRepository = clienteRepository;
        this.pagoFeignClient = pagoFeignClient;
        this.notificacionFeignClient = notificacionFeignClient;
    }

    public List<Cliente> obtenerClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerClientePorId(Long id) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();

            // 1. Llamada sincrónica a pagos
            try {
                cliente.setPagos(pagoFeignClient.obtenerPagosPorClienteId(id));
            } catch (Exception e) {
                cliente.setPagos(List.of());
            }

            // 2. Llamada sincrónica a notificaciones
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
}