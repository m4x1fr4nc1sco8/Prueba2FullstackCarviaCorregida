package cl.duoc.seguro_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class SeguroDTO {

    @NotNull(message = "El ID del vehículo es obligatorio")
    private Long vehiculoId;

    @NotBlank(message = "El nombre del seguro es obligatorio")
    private String nombreSeguro;

    @NotBlank(message = "El tipo de cobertura es obligatorio")
    private String tipoCobertura;

    @NotNull(message = "El costo del seguro es obligatorio")
    @Min(value = 1, message = "El costo debe ser mayor a 0")
    private Double costoSeguro;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @NotBlank(message = "El estado del seguro es obligatorio")
    private String estadoSeguro;

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Premium")
    private String tipoSeguro;

    @Schema(example = "150000")
    private Double costo;

    @Schema(example = "2026-06-21")
    private String fechaVencimiento;

    // GETTERS Y SETTERS

    public Long getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(Long vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public String getNombreSeguro() {
        return nombreSeguro;
    }

    public void setNombreSeguro(String nombreSeguro) {
        this.nombreSeguro = nombreSeguro;
    }

    public String getTipoCobertura() {
        return tipoCobertura;
    }

    public void setTipoCobertura(String tipoCobertura) {
        this.tipoCobertura = tipoCobertura;
    }

    public Double getCostoSeguro() {
        return costoSeguro;
    }

    public void setCostoSeguro(Double costoSeguro) {
        this.costoSeguro = costoSeguro;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstadoSeguro() {
        return estadoSeguro;
    }

    public void setEstadoSeguro(String estadoSeguro) {
        this.estadoSeguro = estadoSeguro;
    }
}