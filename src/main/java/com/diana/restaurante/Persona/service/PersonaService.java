package com.diana.restaurante.Persona.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diana.restaurante.Persona.model.Persona;
import com.diana.restaurante.Persona.repository.PersonalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    private PersonalRepository personaRepository;

    public List<Persona> listar() {
        return personaRepository.findAll();
    }

    public Optional<Persona> obtenerPorId(Long id) {
        return personaRepository.findById(id);
    }

    public Persona guardar(Persona persona) {
        return personaRepository.save(persona);
    }

    public void eliminar(Long id) {
        personaRepository.deleteById(id);
    }

    public boolean existePorDni(String dni) {
        return personaRepository.findByDni(dni).isPresent();
    }

}