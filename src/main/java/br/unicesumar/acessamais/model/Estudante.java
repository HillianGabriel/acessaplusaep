package br.unicesumar.acessamais.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ESTUDANTE")
public class Estudante extends Usuario {

    @Column(length = 30)
    private String matricula;

    @Override
    public String getTipoUsuario() { return "ESTUDANTE"; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}
