package cl.duoc.sucursal_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoDTO {

    private Long id;

    private String patente;

    private String marca;

    private String modelo;

    private int anio;

    private String color;

    private String tipovehiculo;
}