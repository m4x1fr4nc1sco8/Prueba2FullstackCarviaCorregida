package cl.duoc.pago_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación lógica con reserva-service
    private Long reservaId;

    // Relación lógica con usuario-service
    private Long usuarioId;

    private Double monto;

    private LocalDate fechaPago;

    private String metodoPago;

    private String estadoPago;

}