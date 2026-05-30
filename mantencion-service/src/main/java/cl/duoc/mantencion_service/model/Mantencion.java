package cl.duoc.mantencion_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mantenciones")
public class Mantencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El vehiculo es obligatorio")
    @Positive(message = "El id del vehiculo debe ser mayor a 0")
    private Long vehiculoId;

    @NotBlank(message = "La descripcion no puede estar vacia")
    @Size(min = 5, max = 255,
            message = "La descripcion debe tener entre 5 y 255 caracteres")
    private String descripcion;

    @NotNull(message = "La fecha de mantencion es obligatoria")
    private LocalDate fechaMantencion;

    @NotNull(message = "El costo es obligatorio")
    @Positive(message = "El costo debe ser mayor a 0")
    private Double costo;

    @NotBlank(message = "Debe indicar el tipo de mantencion")
    @Size(max = 50,
            message = "El tipo de mantencion no puede superar los 50 caracteres")
    private String tipoMantencion;

    @NotBlank(message = "Debe indicar el estado de la mantencion")
    @Size(max = 30,
            message = "El estado de la mantencion no puede superar los 30 caracteres")
    private String estadoMantencion;
}