package levelUp.levelUp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springdoc.core.configuration.SpringDocHateoasConfiguration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title = "Level-Up Gamer API",
        version = "1.0.0",
        description = "API para el backend del proyecto de e-commerce Level-Up Gamer."
    )
)

@SpringBootApplication
@EnableAutoConfiguration(exclude = { SpringDocHateoasConfiguration.class })
public class LevelUpApplication {

    public static void main(String[] args) {
        SpringApplication.run(LevelUpApplication.class, args);
    }
}