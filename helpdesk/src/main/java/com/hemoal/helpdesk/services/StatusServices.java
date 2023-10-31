package com.hemoal.helpdesk.services;

import java.util.Optional;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hemoal.helpdesk.model.Status;
import com.hemoal.helpdesk.repository.StatusRepository;

@Service
public class StatusServices {
    @Autowired
    StatusRepository statusRepository;

    //TODO -- Excepiton handling in all methods 

    public ResponseEntity<String> criarStatus(Status status){
        String statusNome = status.getNome().toUpperCase();
        status.setNome(statusNome);
        statusRepository.save(status);

        return ResponseEntity.ok().body("Status Criado");
    }

    public ResponseEntity<String> deletarStatus(Integer id){
        statusRepository.deleteById(id);

        return ResponseEntity.ok().body("Status Deletado");
    }

    public ResponseEntity<String> atualizarStatus(Integer id, Status status){
        if (!statusRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        status.setId(id);
        String statusUpper = status.getNome().toUpperCase();
        status.setNome(statusUpper);
        statusRepository.save(status);

        return ResponseEntity.ok().body("Status Atualizado");
    }

    public Optional<Status> pegarStatusId(Integer id){
        return statusRepository.findById(id);
    }

    public List<Status> listarStatus(){
        return statusRepository.findAll();
    }

    
}
