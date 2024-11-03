package com.wedsite.zuong2004.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.wedsite.zuong2004.enums.RolePlay;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String[] publicEntrypoint = { "/api/v1/user/register", "/auth/token", "/auth/introspect",
            "/auth/logout", "/auth/refresh" };

    @Autowired
    CustomJwtDecoder jwtDecoder;

    private static final String userEntrypoint = "/api/v1/user";
    private static final String categoryEntrypoint = "/api/v1/category";
    private static final String orderEntrypoint = "/api/v1/order";
    private static final String productEntrypoint = "/api/v1/product";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpsSecurity) throws Exception {

        httpsSecurity
                .authorizeHttpRequests(
                        request -> request.requestMatchers(HttpMethod.POST, publicEntrypoint).permitAll()
                                .requestMatchers(HttpMethod.GET, userEntrypoint).hasRole(RolePlay.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, userEntrypoint).hasRole(RolePlay.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, userEntrypoint).hasRole(RolePlay.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, categoryEntrypoint).hasRole(RolePlay.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, categoryEntrypoint).hasRole(RolePlay.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, categoryEntrypoint).hasRole(RolePlay.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, categoryEntrypoint).hasRole(RolePlay.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, productEntrypoint).hasRole(RolePlay.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, productEntrypoint).hasRole(RolePlay.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, productEntrypoint).hasRole(RolePlay.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, productEntrypoint).hasRole(RolePlay.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, orderEntrypoint).hasRole(RolePlay.USER.name())
                                .anyRequest().authenticated());

        httpsSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder)
                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));

        httpsSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpsSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
