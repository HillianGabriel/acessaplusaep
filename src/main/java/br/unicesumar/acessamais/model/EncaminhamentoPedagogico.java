package br.unicesumar.acessamais.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PEDAGOGICO")
public class EncaminhamentoPedagogico extends Encaminhamento {

    @Override
    public Integer calcularPrazoSLA() { return 7; }

    @Override
    public String getSetorPadrao() { return "Coordenação Pedagógica"; }
}
