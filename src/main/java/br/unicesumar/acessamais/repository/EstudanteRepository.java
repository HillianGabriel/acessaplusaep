package br.unicesumar.acessamais.repository;

import br.unicesumar.acessamais.model.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudanteRepository extends JpaRepository<Estudante, Integer> { }
