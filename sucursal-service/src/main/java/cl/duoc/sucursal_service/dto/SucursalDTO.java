package cl.duoc.sucursal_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class SucursalDTO {
    @Schema(example = "1")
    private Long id;

    @Schema(example = "Sucursal Santiago Centro")
    private String nombre;

    @Schema(example = "Av. Libertador Bernardo O'Higgins 1234")
    private String direccion;

    @Schema(example = "+56912345678")
    private String telefono;

    @Schema(example = "08:30 - 18:30")
    private String horario;
}
