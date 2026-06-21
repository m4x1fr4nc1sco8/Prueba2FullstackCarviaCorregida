package cl.duoc.reportes_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@OpenAPIDefinition(
		info = @Info(
				title = "Carvia",
				description = """
                        Api para la gestión de Reportes
                        
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
@SpringBootApplication(exclude = {org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration.class})
@EnableFeignClients
public class ReportesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportesServiceApplication.class, args);
	}
}