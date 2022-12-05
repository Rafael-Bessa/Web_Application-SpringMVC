package bessa.morangon.rafael.financeiro.service;

import bessa.morangon.rafael.financeiro.model.Conta;
import bessa.morangon.rafael.financeiro.model.TipoMovimentacao;
import bessa.morangon.rafael.financeiro.model.Transacao;
import bessa.morangon.rafael.financeiro.repository.TransacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Service
public class AnaliseService {
    private TransacaoRepository repository;
    private static final Long valorLimiteParaTransacao = 100000L;
    private static final Long valorLimiteParaContas = 1000000L;
    public List<Transacao> buscaTransacoesSuspeitasMes(String ano, String mes){

        List<Transacao> transacoesSuspeitas = new ArrayList<>();

        List<Transacao> listaMensal = buscaListaCompletaDoMes(ano, mes);

        for (Transacao t: listaMensal) {
            int verifica = t.getValorTransacao().compareTo(new BigDecimal(valorLimiteParaTransacao));

            if(verifica >= 0){
                transacoesSuspeitas.add(t);
            }
        }
    return transacoesSuspeitas;
    }

    public List<Conta> buscaContasOrigemSuspeitasMes(String ano, String mes){

        List<Transacao> listaMensal = buscaListaCompletaDoMes(ano, mes);

        List<Conta> listaTransacoesOrigem = new ArrayList<>();
        Map<Conta,BigDecimal> analiseOrigem = new HashMap<>();
        List<Conta> analiseFinal = new ArrayList<>();

        for (Transacao t: listaMensal) {
            listaTransacoesOrigem.add(new Conta(t.getBancoOrigem(), t.getAgenciaOrigem(), t.getContaOrigem(), t.getValorTransacao(), TipoMovimentacao.SAIDA));
            }

        for (Conta conta: listaTransacoesOrigem) {

            if(analiseOrigem.containsKey(conta)){
                analiseOrigem.put(conta, analiseOrigem.get(conta).add(conta.getValorTransferido()));
            }
            else{
                analiseOrigem.put(conta, conta.getValorTransferido());
            }
        }

        analiseOrigem.entrySet().stream().filter(map -> map.getValue()
                        .compareTo(new BigDecimal(valorLimiteParaContas)) >= 0)
                        .forEach(map -> analiseFinal.add(map.getKey()));

        for (Conta conta : analiseFinal) {
            conta.setValorTransferido(analiseOrigem.get(conta));
        }

    return analiseFinal;

    }

    public List<Conta> buscaContasDestinoSuspeitasMes(String ano, String mes){

        List<Transacao> listaMensal = buscaListaCompletaDoMes(ano, mes);

        List<Conta> listaTransacoesDestino = new ArrayList<>();
        Map<Conta,BigDecimal> analiseDestino = new HashMap<>();
        List<Conta> analiseFinal = new ArrayList<>();

        for (Transacao t: listaMensal) {
            listaTransacoesDestino.add(new Conta(t.getBancoDestino(), t.getAgenciaDestino(), t.getContaDestino(), t.getValorTransacao(), TipoMovimentacao.ENTRADA));
        }

        for (Conta conta: listaTransacoesDestino) {

            if(analiseDestino.containsKey(conta)){
                analiseDestino.put(conta, analiseDestino.get(conta).add(conta.getValorTransferido()));
            }
            else{
                analiseDestino.put(conta, conta.getValorTransferido());
            }
        }

        analiseDestino.entrySet().stream().filter(map -> map.getValue()
                        .compareTo(new BigDecimal(valorLimiteParaContas)) >= 0)
                .forEach(map -> analiseFinal.add(map.getKey()));

        for (Conta conta : analiseFinal) {
            conta.setValorTransferido(analiseDestino.get(conta));
        }

        return analiseFinal;

    }










    private List<Transacao> buscaListaCompletaDoMes(String ano, String mes){
        // Transformei STRING em Integer para usar no LocalDate
        Integer year = Integer.valueOf(ano);
        Integer month = Integer.valueOf(mes);

        // Obter o ultimo dia do mês
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR, year);
        instance.set(Calendar.MONTH, month - 1);

        LocalDateTime primeiroDia = LocalDateTime.of(year, month, 1, 0,0);
        LocalDateTime ultimoDia = LocalDateTime.of(year, month, instance.getActualMaximum(Calendar.DAY_OF_MONTH),23,59);

        List<Transacao> listaMensal = repository.findAllByDataTransacaoBetween(primeiroDia, ultimoDia);
        return listaMensal;
    }


}
