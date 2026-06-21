package cl.duoc.vehiculo_service.dto;

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

    @Schema(
            example = "1",
            description = "Identificador único del vehículo"
    )
    private Long idALong;


    @Schema(
            example = "ABCD12",
            description = "Patente del vehículo"
    )
    private String patenteString;


    @Schema(
            example = "Toyota",
            description = "Marca del vehículo"
    )
    private String marcaString;


    @Schema(
            example = "Corolla",
            description = "Modelo del vehículo"
    )
    private String modeloString;


    @Schema(
            example = "2025",
            description = "Año de fabricación"
    )
    private Integer anioInteger;


    @Schema(
            example = "Blanco",
            description = "Color del vehículo"
    )
    private String colorString;


    @Schema(
            example = "Sedan",
            description = "Tipo de vehículo"
    )
    private String tipoVehiculoString;

}
