package org.agh.edu.pl.carrentalrestapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableConfigurationProperties(CorsFilterProperties.class)
@RequiredArgsConstructor
public class CorsFilterConfiguration {
    private final CorsFilterProperties corsFilterProperties;

    @Bean("corsFilter")
    public CorsFilter corsFilter() {
        final CorsConfiguration config = buildCorsConfiguration();
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    private CorsConfiguration buildCorsConfiguration() {
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);

        if (corsFilterProperties.getMaxAge() != null) {
            config.setMaxAge(corsFilterProperties.getMaxAge());
        }

        if (!CollectionUtils.isEmpty(corsFilterProperties.getAllowedMethods())) {
            config.setAllowedMethods(corsFilterProperties.getAllowedMethods());
        }

        if (!CollectionUtils.isEmpty(corsFilterProperties.getAllowedHeaders())) {
            config.setAllowedHeaders(corsFilterProperties.getAllowedHeaders());
        }

        if (!CollectionUtils.isEmpty(corsFilterProperties.getAllowedOrigins())) {
            config.setAllowedOrigins(corsFilterProperties.getAllowedOrigins());
        }

        return config;
    }
}
