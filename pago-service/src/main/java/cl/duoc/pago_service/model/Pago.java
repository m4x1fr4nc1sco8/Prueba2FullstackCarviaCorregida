package cl.duoc.pago_service.model;

import cl.duoc.pago_service.dto.ClienteDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "ID autogenerado por la base de datos")
    private Long id;

    @NotNull(message = "La reserva es obligatoria")
    @Positive(message = "El id de la reserva debe ser mayor a 0")
    private Long reservaId;

    @NotNull(message = "El cliente es obligatorio")
    @Positive(message = "El id del cliente debe ser mayor a 0")
    @Column(name = "usuario_id")
    private Long clienteId;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    private Double monto;

    @NotNull(message = "La fecha de pago es obligatoria")
    private LocalDate fechaPago;

    @NotBlank(message = "Debe indicar un metodo de pago")
    @Size(max = 50, message = "El metodo de pago no puede superar 50 caracteres")
    private String metodoPago;

    @NotBlank(message = "Debe indicar un estado de pago")
    @Size(max = 30, message = "El estado del pago no puede superar 30 caracteres")
    private String estadoPago;

    @Transient
    private List<ClienteDTO> clientes;
}