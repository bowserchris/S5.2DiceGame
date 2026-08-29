package itacademy.s5t2.diceGame.config;

import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SWAGGER_DESCRIPTION_DEMO;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SWAGGER_DESCRIPTION_EXTERNAL;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SWAGGER_LICENSE;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SWAGGER_SPRINGDOC_URL;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SWAGGER_TITLE;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SWAGGER_URL_STRING;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SWAGGER_VERSION;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

//for swagger localhost:8080/swagger-ui/index.html
//or 			localhost:8080/v3/api-docs/
//				line 37 1. or as listed within the pathselector of the docket? or check application.properties

/**
 * Configuration class for Swagger Documentation
 *
 * @author bowser-chris
 */
@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title(SWAGGER_TITLE).description(SWAGGER_DESCRIPTION_DEMO).version(SWAGGER_VERSION)
						.license(new License().name(SWAGGER_LICENSE).url(SWAGGER_SPRINGDOC_URL)))
				.externalDocs(new ExternalDocumentation()
						.description(SWAGGER_DESCRIPTION_EXTERNAL).url(SWAGGER_URL_STRING));
	}
}