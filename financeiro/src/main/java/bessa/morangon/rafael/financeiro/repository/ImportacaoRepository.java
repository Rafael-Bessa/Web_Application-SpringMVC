package bessa.morangon.rafael.financeiro.repository;

import bessa.morangon.rafael.financeiro.model.Importacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportacaoRepository extends JpaRepository<Importacao, Long> {
}
