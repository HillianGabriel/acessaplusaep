package br.unicesumar.acessamais.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ATITUDINAL")
public class EncaminhamentoAtitudinal extends Encaminhamento {

    @Override
    public Integer calcularPrazoSLA() { return 15; }

    @Override
    public String getSetorPadrao() { return "Núcleo de Acessibilidade"; }
}
