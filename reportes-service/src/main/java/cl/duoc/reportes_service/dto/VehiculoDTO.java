
package cl.duoc.reportes_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private String tipoVehiculo;

    @Schema(example = "Toyota")
    private String marcaString;

    @Schema(example = "Corolla")
    private String modeloString;

    @Schema(example = "2025")
    private Integer anioInteger;

    @Schema(example = "ABCD12")
    private String patenteString;
}