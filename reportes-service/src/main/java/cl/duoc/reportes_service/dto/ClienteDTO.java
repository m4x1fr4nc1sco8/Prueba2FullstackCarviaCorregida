
package cl.duoc.reportes_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    @Schema(example = "1")
    private Long idALong;

    @Schema(example = "Maximiliano")
    private String nombreString;

    @Schema(example = "Araos")
    private String apellidoString;

    @Schema(example = "max.aros@gmail.com")
    private String emailString;
}