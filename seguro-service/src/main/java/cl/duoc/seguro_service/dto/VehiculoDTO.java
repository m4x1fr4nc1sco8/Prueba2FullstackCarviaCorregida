package cl.duoc.seguro_service.dto;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Transient
    private List<VehiculoDTO> vehiculos;
}