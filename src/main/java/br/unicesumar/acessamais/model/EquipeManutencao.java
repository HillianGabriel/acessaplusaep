package br.unicesumar.acessamais.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("EQUIPE_MANUTENCAO")
public class EquipeManutencao extends Usuario {

    @Column(length = 50)
    private String setor;

    @Override
    public String getTipoUsuario() { return "EQUIPE_MANUTENCAO"; }

    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }
}
