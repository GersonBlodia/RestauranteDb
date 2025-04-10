package com.diana.restaurante.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.diana.restaurante.Jwt.JwtService;
import com.diana.restaurante.Persona.model.Persona;
import com.diana.restaurante.Persona.repository.PersonalRepository;
import com.diana.restaurante.User.Role;
import com.diana.restaurante.User.User;
import com.diana.restaurante.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
        private final PersonalRepository personalRepository;
        private final UserRepository userRepository;
        private final JwtService jwtService;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;

        public AuthResponse login(LoginRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
                UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
                String token = jwtService.getToken(user);
                return AuthResponse.builder().token(token).build();
        }

        public AuthResponse register(RegisterRequest request) {
                // Buscar la persona por su dni
                Persona persona = personalRepository.findByDni(request.getDni())
                                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

                // Crear el usuario y asociarlo con la persona
                User user = User.builder()
                                .username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .email(request.getEmail())
                                .persona(persona) // Asociar la persona con el usuario
                                .rol(Role.ALMACENERO)
                                .build();

                userRepository.save(user);

                // Generar el token de autenticación
                return AuthResponse.builder()
                                .token(jwtService.getToken(user))
                                .build();
        }

}