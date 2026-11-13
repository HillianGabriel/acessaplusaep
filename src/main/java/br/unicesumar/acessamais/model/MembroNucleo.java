package br.unicesumar.acessamais.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("MEMBRO_NUCLEO")
public class MembroNucleo extends Usuario {

    @Column(length = 50)
    private String cargo;

    @Override
    public String getTipoUsuario() { return "MEMBRO_NUCLEO"; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
}
