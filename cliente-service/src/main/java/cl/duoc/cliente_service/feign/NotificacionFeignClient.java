package cl.duoc.cliente_service.feign;

import cl.duoc.cliente_service.dto.NotificacionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "notificacion-service", path = "/api/v1/notificaciones")
public interface NotificacionFeignClient {

    // Apunta a la nueva ruta usando PathVariable de forma limpia
    @GetMapping("/usuario/{usuarioId}")
    List<NotificacionDTO> obtenerNotificacionesPorUsuarioId(@PathVariable("usuarioId") Long usuarioId);
}