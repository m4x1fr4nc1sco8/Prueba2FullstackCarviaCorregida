package cl.duoc.vehiculo_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patente;

    private String marca;

    private String modelo;

    private int anio;

    private String color;

    @Column(name = "tipo_vehiculo")
    private String tipoVehiculo;
}