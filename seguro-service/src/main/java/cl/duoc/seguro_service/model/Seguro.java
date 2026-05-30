package cl.duoc.seguro_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seguros")
public class Seguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long vehiculoId;

    private String nombreSeguro;

    private String tipoCobertura;

    private Double costoSeguro;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private String estadoSeguro;

}