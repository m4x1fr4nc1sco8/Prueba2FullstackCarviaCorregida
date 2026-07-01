package cl.duoc.vehiculo_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MantencionDTO {

    private Long id;
    private String tipoMantencion;
    private String descripcion;
    private LocalDate fechaMantencion;
    private Double costo;

    @JsonProperty("estadoMantencion")
    private String estadoVehiculo;
}