package cl.duoc.pago_service.dto;

import lombok.Data;

@Data
public class ReservaDTO {

    private Long id;
    private Long vehiculoId;
    private String fechaInicio;
    private String fechaFin;
    private String estado;
}