package com.diana.restaurante.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.diana.restaurante.Jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final AuthenticationProvider authProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable()) // Desactivar CSRF para las API REST
                                .cors(cors -> cors.configurationSource(
                                                request -> new org.springframework.web.cors.CorsConfiguration()
                                                                .applyPermitDefaultValues())) // Aplica configuración
                                                                                              // CORS
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/auth/**", "/api/**", "/**").permitAll() // Las rutas
                                                                                                           // de
                                                                                                           // autenticación
                                                                                                           // y
                                                                                                           // la API
                                                                                                           // están
                                                                                                           // permitidas
                                                .anyRequest().authenticated()) // El resto requiere autenticación
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin
                                                                                                         // sesiones, ya
                                                                                                         // que es una
                                                                                                         // API REST sin
                                                                                                         // estado
                                .authenticationProvider(authProvider) // Configura el proveedor de autenticación
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Añade
                                                                                                                      // el
                                                                                                                      // filtro
                                                                                                                      // JWT
                                .build();
        }
}