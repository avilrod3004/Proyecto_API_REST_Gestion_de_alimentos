package daw2a.gestion_alimentos_api_rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * Configuración global de CORS (Cross-Origin Resource Sharing) para permitir que
 * el frontend (u otras aplicaciones) accedan a recursos de este backend.
 */
@Configuration
public class WebConfig {

    /**
     * Configura y registra un filtro CORS global para la aplicación.
     * Este filtro permite controlar qué dominios, headers y métodos pueden
     * interactuar con el backend.
     *
     * @return un bean de tipo CorsFilter configurado.
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}