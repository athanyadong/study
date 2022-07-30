package com.athan.gateway.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 *
 */
@Data
@Configuration
@ConfigurationProperties("gateway.swagger")
public class SwaggerRoute {
    private List<SwaggerRouteProperties> routes;
}
