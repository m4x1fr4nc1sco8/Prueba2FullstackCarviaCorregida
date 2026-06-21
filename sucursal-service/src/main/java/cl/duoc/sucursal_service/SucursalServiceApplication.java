package cl.duoc.sucursal_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@OpenAPIDefinition(
		info = @Info(
				title = "Carvia",
				description = """
                        Api para la gestión de Sucursales
                        
                        Contacto:
                        Maximiliano Araos
                        Correo: masi@duoc.cl
                        """,
				version = "1.0.1",
				contact = @Contact(
						name = "Maximiliano Araos",
						email = "masi@duoc.cl"
				)
		)
)

@SpringBootApplication
public class SucursalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SucursalServiceApplication.class, args);
	}

}
