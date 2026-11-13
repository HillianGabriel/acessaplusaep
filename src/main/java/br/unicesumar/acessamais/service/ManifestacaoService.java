package br.unicesumar.acessamais.service;

import br.unicesumar.acessamais.model.*;
import br.unicesumar.acessamais.repository.EstudanteRepository;
import br.unicesumar.acessamais.repository.ManifestacaoRepository;
import br.unicesumar.acessamais.service.Dtos.ManifestacaoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ManifestacaoService {

    private final ManifestacaoRepository manifestacoes;
    private final EstudanteRepository estudantes;

    public ManifestacaoService(ManifestacaoRepository manifestacoes, EstudanteRepository estudantes) {
        this.manifestacoes = manifestacoes;
        this.estudantes = estudantes;
    }

    // CREATE
    public Manifestacao criar(ManifestacaoRequest req) {
        if (req.tipoBarreira() == null || req.local() == null || req.descricao() == null || req.estudanteId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "estudanteId, tipoBarreira, local e descricao são obrigatórios");
        }
        Estudante estudante = estudantes.findById(req.estudanteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudante não encontrado"));
        Manifestacao m = new Manifestacao();
        m.setEstudante(estudante);
        m.setTipoBarreira(req.tipoBarreira());
        m.setLocal(req.local());
        m.setDescricao(req.descricao());
        vincularEncaminhamento(m);
        return manifestacoes.save(m);
    }

    // READ
    @Transactional(readOnly = true)
    public List<Manifestacao> listar() { return manifestacoes.findAll(); }

    @Transactional(readOnly = true)
    public Manifestacao buscar(Integer id) {
        return manifestacoes.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manifestação não encontrada"));
    }

    // UPDATE (dados) — se o tipo de barreira mudar, o SLA é recalculado
    public Manifestacao atualizar(Integer id, ManifestacaoRequest req) {
        Manifestacao m = buscar(id);
        if (req.local() != null) m.setLocal(req.local());
        if (req.descricao() != null) m.setDescricao(req.descricao());
        if (req.tipoBarreira() != null && req.tipoBarreira() != m.getTipoBarreira()) {
            m.setTipoBarreira(req.tipoBarreira());
            vincularEncaminhamento(m);
        }
        return manifestacoes.save(m);
    }

    // UPDATE (status)
    public Manifestacao atualizarStatus(Integer id, StatusManifestacao status) {
        if (status == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "status é obrigatório");
        Manifestacao m = buscar(id);
        m.setStatus(status);
        m.getEncaminhamento().setDataConclusao(status == StatusManifestacao.RESOLVIDA ? LocalDateTime.now() : null);
        return manifestacoes.save(m);
    }

    // DELETE
    public void deletar(Integer id) { manifestacoes.delete(buscar(id)); }

    /** POLIMORFISMO em ação: o SLA vem da subclasse concreta, sem if/else sobre o tipo. */
    private void vincularEncaminhamento(Manifestacao m) {
        Encaminhamento e = switch (m.getTipoBarreira()) {
            case ARQUITETONICA -> new EncaminhamentoEstrutural();
            case PEDAGOGICA -> new EncaminhamentoPedagogico();
            case ATITUDINAL -> new EncaminhamentoAtitudinal();
        };
        e.setManifestacao(m);
        e.setSetorResponsavel(e.getSetorPadrao());
        e.setSlaPrazoDias(e.calcularPrazoSLA());
        m.setEncaminhamento(e);
    }
}
