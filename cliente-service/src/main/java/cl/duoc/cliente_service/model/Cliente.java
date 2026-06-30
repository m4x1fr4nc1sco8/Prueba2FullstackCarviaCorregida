package cl.duoc.cliente_service.model;

import cl.duoc.cliente_service.dto.NotificacionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import cl.duoc.cliente_service.dto.PagoDTO;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "ID autogenerado por la base de datos")
    private Long id;

    @NotBlank(message = "Nombre no puede estar vacio.")
    @Size(min = 2, max = 50, message = "Nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "Apellido no puede estar vacio.")
    @Size(min = 2, max = 50, message = "Apellido debe tener entre 2 y 50 caracteres")
    private String apellido;

    @NotBlank(message = "Email no puede estar vacio.")
    @Email(message = "Debe ingresar un email valido")
    private String email;

    @NotBlank(message = "Contraseña no puede estar vacio.")
    @Size(min = 8, max = 30, message = "La contraseña debe tener entre 8 y 30 caracteres")
    private String contrasenia;

    @NotBlank(message = "Telefono no puede estar vacio.")
    @Pattern(
            regexp = "^[0-9]{8,12}$",
            message = "El telefono debe contener entre 8 y 12 digitos"
    )
    private String telefono;

    @Transient
    private List<PagoDTO> pagos;

    @Transient
    private List<NotificacionDTO> notificaciones;
}