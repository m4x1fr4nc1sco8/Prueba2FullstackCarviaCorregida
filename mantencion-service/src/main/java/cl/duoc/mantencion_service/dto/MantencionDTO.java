package cl.duoc.mantencion_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class MantencionDTO {

    @NotNull(message = "El ID del vehículo es obligatorio")
    private Long vehiculoId;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "La fecha de mantención es obligatoria")
    private LocalDate fechaMantencion;

    @NotNull(message = "El costo es obligatorio")
    @Min(value = 1, message = "El costo debe ser mayor a 0")
    private Double costo;

    @NotBlank(message = "El tipo de mantención es obligatorio")
    private String tipoMantencion;

    @NotBlank(message = "El estado de mantención es obligatorio")
    private String estadoMantencion;


    @Schema(example = "Cambio de aceite")
    private String descripcionString;

    @Schema(example = "2026-06-21")
    private String fecha;

    @Schema(example = "150000")
    private Double costoADouble;


    public Long getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(Long vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaMantencion() {
        return fechaMantencion;
    }

    public void setFechaMantencion(LocalDate fechaMantencion) {
        this.fechaMantencion = fechaMantencion;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getTipoMantencion() {
        return tipoMantencion;
    }

    public void setTipoMantencion(String tipoMantencion) {
        this.tipoMantencion = tipoMantencion;
    }

    public String getEstadoMantencion() {
        return estadoMantencion;
    }

    public void setEstadoMantencion(String estadoMantencion) {
        this.estadoMantencion = estadoMantencion;
    }
}