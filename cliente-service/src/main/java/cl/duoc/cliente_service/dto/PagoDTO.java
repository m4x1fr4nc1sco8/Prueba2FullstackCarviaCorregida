package cl.duoc.cliente_service.dto;

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