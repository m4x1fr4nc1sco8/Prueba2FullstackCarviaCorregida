package cl.duoc.cliente_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nombre no puede estar vacio.")
    @Size(max = 50, message = "Nombre solo puede tener un maximo de 50 caracteres")
    private String nombre;

    private String apellido;

    private String email;

    @NotNull(message = "Contraseña no puede estar vacio.")
    private String contrasenia;

    private String telefono;
}
