package cl.duoc.sucursal_service.model;

import cl.duoc.sucursal_service.dto.VehiculoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sucursales")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "ID autogenerado por la base de datos")
    private Long id;

    @NotBlank(message = "El nombre de la sucursal no puede estar vacio")
    @Size(min = 3, max = 50,
            message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombreSucursal;

    @NotBlank(message = "La direccion no puede estar vacia")
    @Size(min = 5, max = 100,
            message = "La direccion debe tener entre 5 y 100 caracteres")
    private String direccion;

    @NotBlank(message = "La ciudad no puede estar vacia")
    @Size(min = 3, max = 50,
            message = "La ciudad debe tener entre 3 y 50 caracteres")
    private String ciudad;

    @NotBlank(message = "El telefono no puede estar vacio")
    @Pattern(
            regexp = "^[0-9]{8,12}$",
            message = "El telefono debe contener entre 8 y 12 digitos"
    )
    private String telefono;

    @NotBlank(message = "Debe indicar el horario de atencion")
    @Size(max = 100,
            message = "El horario no puede superar los 100 caracteres")
    private String horarioAtencion;

    @NotBlank(message = "Debe indicar el estado de la sucursal")
    @Size(max = 30,
            message = "El estado no puede superar los 30 caracteres")
    private String estadoSucursal;

    @Transient
    private List<VehiculoDTO> vehiculos;
}
