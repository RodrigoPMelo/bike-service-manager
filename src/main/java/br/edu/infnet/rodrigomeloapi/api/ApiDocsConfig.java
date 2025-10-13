package br.edu.infnet.rodrigomeloapi.api;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocsConfig {

    @Bean
    public OpenAPI bikeServiceOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Bike Service Manager API")
                .version("v1.0")
                .description("""
                    REST API for the Bike Service Manager project (academic MVP).  
                    Built with Java 17, Spring Boot, and DDD-inspired architecture.  
                    Features: bikes, clients, mechanics, loaders, and in-memory repositories.
                    """)
                .contact(new Contact()
                    .name("Rodrigo P. Melo")
                    .email("rodrigo.pomper@hotmail.com")
                    .url("https://github.com/RodrigoPMelo/bike-service-manager"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")));
    }
}
