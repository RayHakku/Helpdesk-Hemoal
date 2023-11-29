package com.hemoal.helpdesk.model;

//Java Imports
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.List;
//Jakarta Persistence Imports
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import static jakarta.persistence.CascadeType.ALL;


@Entity
@Table(name = "usuario")
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(
        unique = true,
        nullable = false,
        length = 50
    )
    private String email;
    @Column(
        nullable = false,
        length = 80
    )
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
     inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private List<Role> roles;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "solicitante",
    cascade=ALL
    )
    
    private List<Chamados> chamadosSolicitante;

    public List<Chamados> getChamadosSolicitante() {
        return chamadosSolicitante;
    }
    public void setChamadosSolicitante(List<Chamados> chamadosSolicitante) {
        this.chamadosSolicitante = chamadosSolicitante;
    }
    public List<Chamados> getChamadosTecnico() {
        return chamadosTecnico;
    }
    public void setChamadosTecnico(List<Chamados> chamadosTecnico) {
        this.chamadosTecnico = chamadosTecnico;
    }
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tecnico")
    
    private List<Chamados> chamadosTecnico;
    

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
}
