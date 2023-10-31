package com.hemoal.helpdesk.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hemoal.helpdesk.services.StatusServices;

import com.hemoal.helpdesk.model.Status;

@RestController
@RequestMapping("api/status")
public class StatusController {
    @Autowired
    StatusServices statusServices;

    @GetMapping("/")
    public List<Status> getAllStatus(){
        return statusServices.listarStatus();
    }

    @GetMapping("/{id}")
    public Optional<Status> getStatusById(@PathVariable("id")Integer id){
        return statusServices.pegarStatusId(id);
    }

    @PostMapping("/criar")
    public ResponseEntity<String> createStatus(@RequestBody Status status){
        return statusServices.criarStatus(status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStatus(@PathVariable("id")Integer id){
        return statusServices.deletarStatus(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable("id")Integer id, @RequestBody Status status){
        return statusServices.atualizarStatus(id, status);
    }

    
    
}
