package br.unicesumar.acessamais.controller;

import br.unicesumar.acessamais.model.Manifestacao;
import br.unicesumar.acessamais.service.Dtos.ManifestacaoRequest;
import br.unicesumar.acessamais.service.Dtos.StatusRequest;
import br.unicesumar.acessamais.service.ManifestacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manifestacoes")
public class ManifestacaoController {

    private final ManifestacaoService service;

    public ManifestacaoController(ManifestacaoService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Manifestacao criar(@RequestBody ManifestacaoRequest req) { return service.criar(req); }

    @GetMapping
    public List<Manifestacao> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public Manifestacao buscar(@PathVariable Integer id) { return service.buscar(id); }

    @PutMapping("/{id}")
    public Manifestacao atualizar(@PathVariable Integer id, @RequestBody ManifestacaoRequest req) {
        return service.atualizar(id, req);
    }

    @PatchMapping("/{id}/status")
    public Manifestacao atualizarStatus(@PathVariable Integer id, @RequestBody StatusRequest req) {
        return service.atualizarStatus(id, req.status());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) { service.deletar(id); }
}
