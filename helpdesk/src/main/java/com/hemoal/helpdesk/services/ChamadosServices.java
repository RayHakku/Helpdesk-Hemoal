package com.hemoal.helpdesk.services;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hemoal.helpdesk.model.Chamados;
import com.hemoal.helpdesk.model.Status;
import com.hemoal.helpdesk.model.Usuario;
import com.hemoal.helpdesk.repository.ChamadosRepository;
import com.hemoal.helpdesk.repository.StatusRepository;
import com.hemoal.helpdesk.security.JWTAuthFilter;

import jakarta.persistence.EntityManager;
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
    @Autowired
    EntityManager EntityManager;

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
    @Transactional
    public ResponseEntity<String> deletarChamado(Long id){
        EntityManager.createNativeQuery("DELETE FROM chamados WHERE id = ?1").setParameter(1, id).executeUpdate();
        return ResponseEntity.ok().body("Chamado Deletado");
    }


    @Transactional
    public List<Chamados> listarChamados(){
        return chamadosRepository.findAll();
    }

    public Optional<Chamados> pegarChamadoId(Long id){
        return chamadosRepository.findById(id);
    }

    public ResponseEntity<String> atualizarChamado(Long id, Chamados chamado) {
        if (!chamadosRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        
        Status status = statusRepository.findByNome(chamado.getStatus().getNome().toUpperCase());
        if (status == null) {
            return ResponseEntity.badRequest().body("Status não encontrado");
        }

    
        Optional<Usuario> solicitante = usuarioService.getUsuarioByEmail(chamado.getSolicitante().getEmail());
        if (!solicitante.isPresent()) {
            return ResponseEntity.badRequest().body("Solicitante não encontrado");
        }

        
        Optional<Usuario> tecnico = Optional.empty();
        if (chamado.getTecnico() != null) {
            tecnico = usuarioService.getUsuarioByEmail(chamado.getTecnico().getEmail());
        }

        
        chamado.setSolicitante(solicitante.get());
        chamado.setStatus(status);
        chamado.setId(id);

        if (tecnico.isPresent()) {
            chamado.setTecnico(tecnico.get());
        }

        
        String statusNome = status.getNome().toUpperCase();
        if (statusNome.equals("CONCLUIDO")) {
            chamado.setDataInicioAtendimento(chamado.getDataInicioAtendimento());
            chamado.setDataFechamento(LocalDateTime.now());
        } else if (statusNome.equals("EM PROGRESSO")) {
            chamado.setDataInicioAtendimento(LocalDateTime.now());
        }

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
