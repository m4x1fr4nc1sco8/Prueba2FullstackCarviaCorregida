package cl.duoc.seguro_service.model;

import cl.duoc.seguro_service.dto.VehiculoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seguros")
public class Seguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El vehiculo es obligatorio")
    @Positive(message = "El id del vehiculo debe ser mayor a 0")
    private Long vehiculoId;

    @NotBlank(message = "El nombre del seguro no puede estar vacio")
    @Size(min = 3, max = 50,
            message = "El nombre del seguro debe tener entre 3 y 50 caracteres")
    private String nombreSeguro;

    @NotBlank(message = "Debe indicar un tipo de cobertura")
    @Size(max = 50,
            message = "El tipo de cobertura no puede superar los 50 caracteres")
    private String tipoCobertura;

    @NotNull(message = "El costo del seguro es obligatorio")
    @Positive(message = "El costo del seguro debe ser mayor a 0")
    private Double costoSeguro;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de termino es obligatoria")
    private LocalDate fechaFin;

    @NotBlank(message = "Debe indicar el estado del seguro")
    @Size(max = 30,
            message = "El estado del seguro no puede superar los 30 caracteres")
    private String estadoSeguro;

    @Transient
    private List<VehiculoDTO> vehiculos;
}
