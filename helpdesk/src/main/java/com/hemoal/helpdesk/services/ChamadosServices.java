package com.hemoal.helpdesk.services;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hemoal.helpdesk.model.Chamados;
import com.hemoal.helpdesk.model.Status;
import com.hemoal.helpdesk.model.Usuario;
import com.hemoal.helpdesk.repository.ChamadosRepository;
import com.hemoal.helpdesk.repository.StatusRepository;
import com.hemoal.helpdesk.security.JWTAuthFilter;

import jakarta.transaction.Transactional;



@Service
public class ChamadosServices {
    @Autowired
    ChamadosRepository chamadosRepository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    JWTAuthFilter jwtAuthFilter;

    //TODO -- Excepiton handling in all methods

    public ResponseEntity<String> criarChamado(Chamados chamado, String jwtToken){
        try {
            if(StringUtils.hasText(jwtToken)&& jwtToken.startsWith("Bearer ")){
                    jwtToken = jwtToken.substring(7, jwtToken.length());
                }
             
            Usuario solicitante = usuarioService.getUsuarioByEmailWithToken(jwtToken).orElseThrow(() -> new RuntimeException("User not found"));
            chamado.setSolicitante(solicitante);

            Status status = statusRepository.findByNome("ABERTO");
            chamado.setStatus(status);

            chamadosRepository.save(chamado);

            return ResponseEntity.ok().body("Chamado Criado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<String> deletarChamado(Long id){
        chamadosRepository.deleteById(id);

        return ResponseEntity.ok().body("Chamado Deletado");
    }

    @Transactional
    public List<Chamados> listarChamados(){
        return chamadosRepository.findAll();
    }

    public Optional<Chamados> pegarChamadoId(Long id){
        return chamadosRepository.findById(id);
    }

    public ResponseEntity<String> atualizarChamado(Long id, Chamados chamado){
        if (!chamadosRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        chamado.setId(id);
        chamadosRepository.save(chamado);

        return ResponseEntity.ok().body("Chamado Atualizado");
    }

    public void atulizarStatusChamado(Long id, String nome){
        Chamados chamado = chamadosRepository.findById(id).get();
        nome = nome.toUpperCase();
        Status status = statusRepository.findByNome(nome);
        chamado.setStatus(status);
        chamadosRepository.save(chamado);
    }

    

}
