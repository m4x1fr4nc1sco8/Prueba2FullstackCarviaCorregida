package cl.duoc.seguro_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SeguroDTO {

    private Long id;

    private String tipoSeguro;

    private String cobertura;

    private Double montoCobertura;

    private LocalDate fechaInicio;

    private LocalDate fechaVencimiento;

    private String estado;
}