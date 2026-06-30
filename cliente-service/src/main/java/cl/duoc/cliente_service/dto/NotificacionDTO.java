package cl.duoc.cliente_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NotificacionDTO {
    private Long id;
    private Long usuarioId;
    private String mensaje;
    private String fechaEnvio;
    private String estadoNotificacion;

    @JsonProperty("tipoNotificacion")
    private String tipoNotificacion;
}