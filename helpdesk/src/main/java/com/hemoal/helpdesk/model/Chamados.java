package com.hemoal.helpdesk.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "chamados")
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public class Chamados {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(
        nullable = false,
        length = 50
    )
    private String titulo;
    @Column(
        nullable = false,
        length = 200
    )
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
    
    @Column(
        nullable = false
    )
    private LocalDateTime dataAbertura;
    private LocalDateTime dataInicioAtendimento;
    private LocalDateTime dataFechamento;
    

    @ManyToOne
    @JoinColumn(name = "solicitante_id")
    @JsonSerialize(using = UsuarioSerializer.class)
    @JsonDeserialize(using = UsuarioDeserializer.class)
    private Usuario solicitante;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    @JsonSerialize(using = UsuarioSerializer.class)
    @JsonDeserialize(using = UsuarioDeserializer.class)
    private Usuario tecnico;
    
    @PrePersist
    public void prePersist() {
        setDataAbertura(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getDataInicioAtendimento() {
        return dataInicioAtendimento;
    }

    public void setDataInicioAtendimento(LocalDateTime dataInicioAtendimento) {
        this.dataInicioAtendimento = dataInicioAtendimento;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }

    public Usuario getTecnico() {
        return tecnico;
    }

    public void setTecnico(Usuario tecnico) {
        this.tecnico = tecnico;
    }

}
