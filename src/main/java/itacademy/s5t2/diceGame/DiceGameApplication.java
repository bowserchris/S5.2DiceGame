package itacademy.s5t2.diceGame;

import static itacademy.s5t2.diceGame.constants.CommonConstants.CURRENT_VERSION;
import static itacademy.s5t2.diceGame.constants.CommonConstants.PACKAGE_BUSINESS_REPO;
import static itacademy.s5t2.diceGame.constants.CommonConstants.PACKAGE_SECURITY_REPO;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.CONTACT_EMAIL;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.CONTACT_NAME;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.LICENSE_NAME;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.LICENSE_URL;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SECURITY_FORMAT;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SECURITY_NAME_BEARER;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SECURITY_NAME_JWT;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SECURITY_SCHEME_BASIC;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SECURITY_SCHEME_BEARER;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SWAGGER_DESCRIPTION;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.SWAGGER_TITLE;
import static itacademy.s5t2.diceGame.constants.SwaggerConstants.TERMS_SERVICE;

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

// for swagger localhost:8080/swagger-ui/index.html  or localhost:8080/v3/api-docs/
//@EntityScan(basePackages = {"itacademy.s5t2.diceGame"})
//@ComponentScan

@OpenAPIDefinition(info = @Info(title = SWAGGER_TITLE, version = CURRENT_VERSION, description = SWAGGER_DESCRIPTION, termsOfService = TERMS_SERVICE, contact = @Contact(name = CONTACT_NAME, email = CONTACT_EMAIL), license = @License(name = LICENSE_NAME, url = LICENSE_URL))
		) //servers = {@Server(url = "http://localhost:9003"), @Server(url = "http://localhost:8080")}
@SecurityScheme(name = SECURITY_NAME_JWT, scheme = SECURITY_SCHEME_BASIC,
type = SecuritySchemeType.HTTP,
		in = SecuritySchemeIn.HEADER)
@SecurityScheme(name = SECURITY_NAME_BEARER, scheme = SECURITY_SCHEME_BEARER,
type = SecuritySchemeType.HTTP,
bearerFormat = SECURITY_FORMAT,
in = SecuritySchemeIn.HEADER)
@EnableJpaRepositories(basePackages = { PACKAGE_BUSINESS_REPO, PACKAGE_SECURITY_REPO })
@EnableAutoConfiguration
@EnableMongoRepositories(PACKAGE_BUSINESS_REPO)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) //or change to swagger config? or , scanBasePackages = "itacademy.s5t2.diceGame"
public class DiceGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiceGameApplication.class, args);
	}
}
