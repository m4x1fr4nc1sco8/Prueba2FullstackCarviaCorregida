package cl.duoc.cliente_service.controller;

import cl.duoc.cliente_service.dto.ClienteDTO;
import cl.duoc.cliente_service.model.Cliente;
import cl.duoc.cliente_service.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(
        name = "Clientes",
        description = "Operaciones disponibles para la gestión de clientes"
)

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(
            summary = "Listar clientes",
            description = "Obtiene el listado completo de clientes registrados en el sistema."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Clientes listados correctamente",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = Cliente.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.obtenerClientes();
    }

    @Operation(
            summary = "Obtener cliente",
            description = "Obtiene un cliente específico mediante su ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Cliente encontrado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Cliente.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Cliente no encontrado",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @GetMapping("/{id}")
    public Optional<Cliente> obtenerCliente(@PathVariable Long id) {
        return clienteService.obtenerClientePorId(id);
    }

    @Operation(
            summary = "Crear cliente",
            description = "Registra un nuevo cliente en el sistema."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Cliente creado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Cliente.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos enviados"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor"
    )
    @PostMapping
    public Cliente guardarCliente(@RequestBody Cliente cliente) {
        return clienteService.guardarCliente(cliente);
    }

    @Operation(
            summary = "Actualizar cliente",
            description = "Actualiza la información de un cliente existente."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Cliente actualizado correctamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Cliente.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Cliente no encontrado"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos"
    )
    @PutMapping("/{id}")
    public Cliente actualizarCliente(
            @PathVariable Long id,
            @RequestBody ClienteDTO dto
    ){

        Cliente cliente = new Cliente();

        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());

        return clienteService.actualizarCliente(id, cliente);
    }

    // ELIMINAR CLIENTE
    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
    }
}