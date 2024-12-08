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
                        .allowedOriginPatterns("*") // Permite qualquer origem
                        //.allowedOrigins("http://localhost:4200") // Permite origens específicas
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                        .allowedHeaders("*") ;// Permite todos os cabeçalhos
                     //   .allowCredentials(true); // Permite envio de cookies
            }
        };
    }
}


