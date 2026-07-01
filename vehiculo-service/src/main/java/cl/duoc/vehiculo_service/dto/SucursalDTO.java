package cl.duoc.vehiculo_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SucursalDTO {

    private Long id;

    @JsonProperty("nombreSucursal")
    private String nombre;
    private String direccion;
    private String telefono;
    private String ciudad;
}