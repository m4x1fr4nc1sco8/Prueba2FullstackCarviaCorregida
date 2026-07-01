package cl.duoc.vehiculo_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDate;

@Data
public class SeguroDTO {

    private Long id;

    @JsonProperty("nombreSeguro")
    private String tipoSeguro;

    @JsonProperty("tipoCobertura")
    private String cobertura;

    @JsonProperty("costoSeguro")
    private Double montoCobertura;

    @JsonProperty("fechaInicio")
    private LocalDate fechaInicio;

    @JsonProperty("fechaFin")
    private LocalDate fechaVencimiento;

    @JsonProperty("estadoSeguro")
    private String estado;
}