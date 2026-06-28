package cl.duoc.vehiculo_service.model;


import cl.duoc.vehiculo_service.dto.MantencionDTO;
import cl.duoc.vehiculo_service.dto.SeguroDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import cl.duoc.vehiculo_service.dto.SucursalDTO;
import jakarta.persistence.Transient;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La patente no puede estar vacia")
    @Pattern(
            regexp = "^[A-Z]{4}[0-9]{2}$",
            message = "La patente debe tener formato ABCD12"
    )
    private String patente;

    @NotBlank(message = "La marca no puede estar vacia")
    @Size(min = 2, max = 50,
            message = "La marca debe tener entre 2 y 50 caracteres")
    private String marca;

    @NotBlank(message = "El modelo no puede estar vacio")
    @Size(min = 2, max = 50,
            message = "El modelo debe tener entre 2 y 50 caracteres")
    private String modelo;

    @Min(value = 1900, message = "El año debe ser mayor a 1900")
    @Max(value = 2035, message = "El año ingresado no es valido")
    private int anio;

    @NotBlank(message = "El color no puede estar vacio")
    @Size(max = 30,
            message = "El color no puede superar los 30 caracteres")
    private String color;

    @NotNull(message = "La sucursal es obligatoria")
    @Positive(message = "El id de la sucursal debe ser mayor a 0")
    private Long sucursalId;

    @NotBlank(message = "Debe indicar el tipo de vehiculo")
    @Size(max = 30,
            message = "El tipo de vehiculo no puede superar los 30 caracteres")
    @Column(name = "tipo_vehiculo")
    private String tipoVehiculo;

    @Transient
    private SeguroDTO seguro;

    @Transient
    private List<MantencionDTO> mantenciones;

    @Transient
    private SucursalDTO sucursal;
}