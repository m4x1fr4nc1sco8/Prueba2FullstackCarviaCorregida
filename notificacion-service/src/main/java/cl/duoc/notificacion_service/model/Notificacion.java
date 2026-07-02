package cl.duoc.notificacion_service.model;

import cl.duoc.notificacion_service.dto.ClienteDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "notificaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "ID autogenerado por la base de datos")
    private Long id;

    @NotNull(message = "El id del usuario no puede ser nulo")
    @Positive(message = "El id del usuario debe ser mayor que cero")
    private Long usuarioId;

    @NotBlank(message = "El mensaje no puede estar vacio")
    @Size(min = 5, max = 255,
            message = "El mensaje debe tener entre 5 y 255 caracteres")
    private String mensaje;

    @NotBlank(message = "Debe indicar un tipo de notificacion")
    @Size(max = 50,
            message = "El tipo de notificacion no puede superar 50 caracteres")
    private String tipoNotificacion;

    @NotNull(message = "La fecha de envio es obligatoria")
    private LocalDate fechaEnvio;

    @NotBlank(message = "Debe indicar el estado de la notificacion")
    @Size(max = 30,
            message = "El estado de la notificacion no puede superar 30 caracteres")
    private String estadoNotificacion;

    @Transient
    @io.swagger.v3.oas.annotations.media.Schema(hidden = true)
    private List<ClienteDTO> clientes;
}