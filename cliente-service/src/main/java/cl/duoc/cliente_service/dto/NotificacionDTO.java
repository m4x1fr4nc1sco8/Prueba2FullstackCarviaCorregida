package cl.duoc.cliente_service.dto;

import lombok.Data;

@Data
public class NotificacionDTO {

    private Long id;
    private String mensaje;
    private String tipo;
}
