package bessa.morangon.rafael.financeiro.repository;

import bessa.morangon.rafael.financeiro.model.Importacao;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ImportacaoRepository extends JpaRepository<Importacao, Long> {

    @Override
    @EntityGraph(attributePaths = {"usuario"})
    List<Importacao> findAll();

    @Override
    @EntityGraph(attributePaths = {"usuario"})
    Optional<Importacao> findById(Long id);

    @EntityGraph(attributePaths = {"usuario"})
    Optional<Importacao> findByDataTransacao(LocalDateTime data);
}
