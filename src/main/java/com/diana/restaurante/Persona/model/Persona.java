package com.diana.restaurante.Persona.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "persona")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombres;
    @Column(nullable = false)
    private String apellidos;
    @Column(unique = true, length = 15)
    private String dni;
    private String telefono;
    private String direccion;
    private LocalDate fechaNacimiento;

}
