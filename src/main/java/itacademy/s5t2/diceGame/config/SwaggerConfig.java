package itacademy.s5t2.diceGame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import itacademy.s5t2.diceGame.constants.CommonConstants;

//for swagger localhost:8080/swagger-ui/index.html
//or 			localhost:8080/v3/api-docs/
//				line 37 1. or as listed within the pathselector of the docket? or check application.properties

@Configuration
//@Profile({"!prod && swagger"})
public class SwaggerConfig {
	
	@Bean
	OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title(CommonConstants.SOFTWARE_NAME + " API")
						.description(CommonConstants.SOFTWARE_NAME + " demo application")
						.version("v1.0.3")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation()
						.description(CommonConstants.SOFTWARE_NAME + " Wiki Documentation")
						.url("website"));
	}
	
	/*@Bean
	Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.paths(PathSelectors.ant("/api/*"))	//1.	//.any() selects all methods //is this only swagger controller, or do /auth, /users, /players neeed to be here too?
					.apis(RequestHandlerSelectors.basePackage("itacademy.s5t2.diceGame"))
					.build();
	}*/
	
}
