package cl.duoc.mantencion_service.model;

import jakarta.persistence.*;
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

    private Long vehiculoId;

    private String descripcion;

    private LocalDate fechaMantencion;

    private Double costo;

    private String tipoMantencion;


    private String estadoMantencion;

}