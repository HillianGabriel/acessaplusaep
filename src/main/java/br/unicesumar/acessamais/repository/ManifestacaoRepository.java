package br.unicesumar.acessamais.repository;

import br.unicesumar.acessamais.model.Manifestacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ManifestacaoRepository extends JpaRepository<Manifestacao, Integer> {
    List<Manifestacao> findByEstudanteId(Integer estudanteId);
}
