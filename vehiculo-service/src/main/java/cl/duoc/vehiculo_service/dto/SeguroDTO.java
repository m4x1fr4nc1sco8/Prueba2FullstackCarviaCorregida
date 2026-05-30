package cl.duoc.vehiculo_service.dto;

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