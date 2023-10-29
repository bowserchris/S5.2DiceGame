package itacademy.s5t2.diceGame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import itacademy.s5t2.diceGame.constants.CommonConstants;

// for swagger localhost:8080/swagger-ui/index.html  or localhost:8080/v3/api-docs/
//@EntityScan(basePackages = {"itacademy.s5t2.diceGame"})
//@ComponentScan

//swagger annotations below, as well as any @Opertion, apiresponses, apiresponse are also swagger annotations
@OpenAPIDefinition(info = @Info(title = CommonConstants.SOFTWARE_NAME + " API", 
								version = "1.3", 
								description = "API documentation for Sprint 5.2 Dice Game Project",
								termsOfService = "Free to use",
								contact = @Contact(name = "Christian", email = "email.com"),
								license = @License(name = "API License", 
											url = "Affiliated " + CommonConstants.SOFTWARE_NAME + " website to be placed here")))
/*@SecurityScheme(name = "jwtopenapi", 
				scheme = "basic", 
				type = SecuritySchemeType.HTTP, 
				in = SecuritySchemeIn.HEADER) //type = )
@SecurityScheme(name = "Bearer Authentication",
				type = SecuritySchemeType.HTTP,
				bearerFormat = "JWT",
				scheme = "bearer")*/
@EnableJpaRepositories(basePackages = {"itacademy.s5t2.diceGame.businessLayer.repository", "itacademy.s5t2.diceGame.securityLayer.repository"})
@EnableAutoConfiguration
@EnableMongoRepositories("itacademy.s5t2.diceGame.businessLayer.repository")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) //or change to swagger config? or , scanBasePackages = "itacademy.s5t2.diceGame"
public class DiceGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiceGameApplication.class, args);
	}
	
}
