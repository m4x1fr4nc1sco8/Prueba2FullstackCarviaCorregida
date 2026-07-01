package cl.duoc.vehiculo_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@OpenAPIDefinition(
		info = @Info(
				title = "Carvia",
				description = """
                        Api para la gestión de Vehiculos
                        
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
@EnableDiscoveryClient
@EntityScan(basePackages = "cl.duoc.vehiculo_service.model")
@EnableJpaRepositories(basePackages = "cl.duoc.vehiculo_service.repository")
@SpringBootApplication
@EnableFeignClients
public class VehiculoServiceApplication {

	//aaaaaaa
	public static void main(String[] args) {
		SpringApplication.run(VehiculoServiceApplication.class, args);
	}

}
