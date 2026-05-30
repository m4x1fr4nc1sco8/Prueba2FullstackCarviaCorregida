package cl.duoc.pago_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PagoDTO {

    private Long id;
    private Double monto;
    private LocalDate fechaPago;
    private String metodoPago;
    private String estadoPago;
}