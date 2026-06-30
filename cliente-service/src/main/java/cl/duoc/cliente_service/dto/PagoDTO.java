package cl.duoc.cliente_service.dto;

import lombok.Data;

@Data
public class PagoDTO {
    private Long id;
    private Long reservaId;
    private Long clienteId;
    private Double monto;
    private String fechaPago;
    private String metodoPago;
    private String estadoPago;
}