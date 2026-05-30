package cl.duoc.sucursal_service.dto;

import lombok.Data;

@Data
public class SucursalDTO {

    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String ciudad;
}