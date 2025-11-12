package bessa.morangon.rafael.financeiro.repository;

import bessa.morangon.rafael.financeiro.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findAllByDataTransacaoBetween(LocalDateTime inicio, LocalDateTime fim);

    Optional<Transacao> findByDataTransacao(LocalDateTime data);
}
