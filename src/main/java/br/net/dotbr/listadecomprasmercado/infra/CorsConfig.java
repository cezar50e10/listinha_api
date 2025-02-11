package br.net.dotbr.listadecomprasmercado.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permite todas as rotas
                        .allowedOrigins("http://localhost") // Altere se necessário
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Inclui OPTIONS
                        .allowedHeaders("*")
                        .allowCredentials(true); // Permite cookies e autenticação
            }
        };
    }
}


