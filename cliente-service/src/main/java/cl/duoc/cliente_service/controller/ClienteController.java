package cl.duoc.cliente_service.controller;

import cl.duoc.cliente_service.exception.ClienteNotExistException;
import cl.duoc.cliente_service.model.Cliente;
import cl.duoc.cliente_service.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                    array = @ArraySchema(schema = @Schema(implementation = Cliente.class))
            )
    )
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.obtenerClientes();
        return ResponseEntity.ok(clientes);
    }

    @Operation(
            summary = "Obtener cliente",
            description = "Obtiene un cliente específico mediante su ID, incluyendo sus listas de pagos y notificaciones consultadas sincrónicamente."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Cliente encontrado correctamente con su historial consolidado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
    )
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(
            @Parameter(description = "ID único del cliente a buscar (Ejemplo: 30 para Sofía)", example = "30")
            @PathVariable Long id
    ) {
        Cliente cliente = clienteService.obtenerClientePorId(id)
                .orElseThrow(() -> new ClienteNotExistException("Cliente no encontrado"));
        return ResponseEntity.ok(cliente);
    }

    @Operation(
            summary = "Crear cliente",
            description = "Registra un nuevo cliente en el sistema."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Cliente creado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
    )
    @ApiResponse(responseCode = "400", description = "Datos inválidos enviados")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @PostMapping
    public ResponseEntity<Cliente> guardarCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.guardarCliente(cliente);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Eliminar cliente",
            description = "Elimina un cliente del sistema usando su ID."
    )
    @ApiResponse(responseCode = "204", description = "Cliente eliminado con éxito (Sin contenido)")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(
            @Parameter(description = "ID único del cliente a eliminar (Ejemplo: 30)", example = "30")
            @PathVariable Long id
    ) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Buscar clientes por apellido",
            description = "Filtra de manera personalizada los clientes cuyo apellido contenga el texto ingresado."
    )
    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarClientesPorApellido(
            @io.swagger.v3.oas.annotations.Parameter(description = "Apellido o parte de él", example = "Ugarte")
            @RequestParam String apellido
    ) {
        return ResponseEntity.ok(clienteService.buscarPorApellido(apellido));
    }
}