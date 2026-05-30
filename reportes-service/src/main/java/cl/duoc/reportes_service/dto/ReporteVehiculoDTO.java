
package cl.duoc.reportes_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteVehiculoDTO {

    // Datos vehículo
    private Long vehiculoId;
    private String patente;
    private String marca;
    private String modelo;
    private int anio;
    private String color;
    private String tipoVehiculo;

    // Datos cliente
    private Long clienteId;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
}