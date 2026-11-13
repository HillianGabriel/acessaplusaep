package br.unicesumar.acessamais.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ESTRUTURAL")
public class EncaminhamentoEstrutural extends Encaminhamento {

    @Override
    public Integer calcularPrazoSLA() { return 3; }

    @Override
    public String getSetorPadrao() { return "Manutenção"; }
}
