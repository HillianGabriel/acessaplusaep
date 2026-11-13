package br.unicesumar.acessamais.controller;

import br.unicesumar.acessamais.model.*;
import br.unicesumar.acessamais.repository.UsuarioRepository;
import br.unicesumar.acessamais.service.Dtos.UsuarioRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarios;

    public UsuarioController(UsuarioRepository usuarios) { this.usuarios = usuarios; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario criar(@RequestBody UsuarioRequest req) {
        if (req.tipo() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "tipo é obrigatório");
        if (req.nome() == null || req.email() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nome e email são obrigatórios");
        }
        if (usuarios.existsByEmail(req.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um usuário com este e-mail");
        }
        Usuario u = switch (req.tipo()) {
            case "ESTUDANTE" -> { Estudante e = new Estudante(); e.setMatricula(req.extra()); yield e; }
            case "MEMBRO_NUCLEO" -> { MembroNucleo m = new MembroNucleo(); m.setCargo(req.extra()); yield m; }
            case "EQUIPE_MANUTENCAO" -> { EquipeManutencao q = new EquipeManutencao(); q.setSetor(req.extra()); yield q; }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "tipo inválido");
        };
        u.setNome(req.nome());
        u.setEmail(req.email());
        return usuarios.save(u);
    }

    @GetMapping
    public List<Usuario> listar() { return usuarios.findAll(); }
}
