package com.hemoal.helpdesk.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class UsuarioSerializer extends StdSerializer<Usuario> {

    public UsuarioSerializer() {
        this(null);
    }


    public UsuarioSerializer(Class<Usuario> t) {
        super(t);
    }

    @Override
    public void serialize(
      Usuario value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException, JsonProcessingException {
  
        jgen.writeString(value.getEmail());
    }
    
}
