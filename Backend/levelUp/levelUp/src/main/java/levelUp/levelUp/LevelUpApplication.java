package levelUp.levelUp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

// Importa la clase que está fallando
import org.springdoc.core.configuration.SpringDocHateoasConfiguration;
// 1. Importa estas clases
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;

// 2. Añade esta anotación para configurar tu API
@OpenAPIDefinition(
    info = @Info(
        title = "Level-Up Gamer API",
        version = "1.0.0",
        description = "API para el backend del proyecto de e-commerce Level-Up Gamer.",
        contact = @Contact(
            name = "Benjamín", 
            email = "tu-email@dominio.com"
        ),
        license = @License(
            name = "Licencia (ej. Apache 2.0)", 
            url = "http://www.apache.org/licenses/LICENSE-2.0.html"
        )
    )
)

@SpringBootApplication
// Añade esta línea para excluir la configuración problemática
@EnableAutoConfiguration(exclude = { SpringDocHateoasConfiguration.class })
public class LevelUpApplication {

    public static void main(String[] args) {
        SpringApplication.run(LevelUpApplication.class, args);
    }
}