package com.hemoal.helpdesk.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hemoal.helpdesk.repository.UsuarioRepo;

import io.jsonwebtoken.io.IOException;

public class UsuarioDeserializer extends StdDeserializer<Usuario> {

    @Autowired
    private UsuarioRepo usuarioRepository;

    public UsuarioDeserializer() {
        this(null);
    }

    public UsuarioDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Usuario deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, java.io.IOException {
        String email = jp.getText();
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }
}