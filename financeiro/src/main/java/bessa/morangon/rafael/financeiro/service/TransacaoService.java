package bessa.morangon.rafael.financeiro.service;

import bessa.morangon.rafael.financeiro.model.Transacao;
import bessa.morangon.rafael.financeiro.repository.TransacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class TransacaoService {
    private TransacaoRepository repository;
    public List<Transacao> buscaTodasTransacoesNaMesmaData(LocalDateTime data){

        LocalDateTime inicio = LocalDateTime.of(data.getYear(),data.getMonthValue(), data.getDayOfMonth(), 0, 0);

        LocalDateTime fim = LocalDateTime.of(data.getYear(), data.getMonthValue(), data.getDayOfMonth(), 23, 59);

        List<Transacao> allByDataTransacao = repository.findAllByDataTransacaoBetween(inicio,fim);

        return allByDataTransacao;
        }
    }

