package br.unicesumar.acessamais.service;

import br.unicesumar.acessamais.model.StatusManifestacao;
import br.unicesumar.acessamais.model.TipoBarreira;

public final class Dtos {
    private Dtos() { }

    public record ManifestacaoRequest(Integer estudanteId, TipoBarreira tipoBarreira, String local, String descricao) { }
    public record StatusRequest(StatusManifestacao status) { }
    /** tipo: ESTUDANTE | MEMBRO_NUCLEO | EQUIPE_MANUTENCAO. "extra" = matrícula, cargo ou setor, conforme o tipo. */
    public record UsuarioRequest(String tipo, String nome, String email, String extra) { }
}
