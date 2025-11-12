package bessa.morangon.rafael.financeiro.controller;

import bessa.morangon.rafael.financeiro.dto.DataAnaliseDto;
import bessa.morangon.rafael.financeiro.model.Conta;
import bessa.morangon.rafael.financeiro.model.Transacao;
import bessa.morangon.rafael.financeiro.service.AnaliseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/analise")
public class AnaliseController {

    private AnaliseService service;

    @GetMapping
    public String telaAnalise() {
        return "analise";
    }

    @PostMapping
    public String analise(DataAnaliseDto d, Model model) {

        // Parse do formato yyyy-MM (ex: 2022-01)
        String[] datas = d.getData().split("-");
        String ano = datas[0];
        String mes = datas[1];

        // Buscar transações e contas suspeitas
        List<Transacao> transacoes = service.buscaTransacoesSuspeitasMes(ano, mes);
        List<Conta> contasOrigem = service.buscaContasOrigemSuspeitasMes(ano, mes);
        List<Conta> contasDestino = service.buscaContasDestinoSuspeitasMes(ano, mes);

        // Formatar período para exibição
        String periodoAnalisado = String.format("%s/%s", mes, ano);

        // Adicionar ao model
        model.addAttribute("transacoesSuspeitas", transacoes);
        model.addAttribute("contasSuspeitasOrigem", contasOrigem);
        model.addAttribute("contasSuspeitasDestino", contasDestino);
        model.addAttribute("periodoAnalisado", periodoAnalisado);

        // Adicionar contadores
        model.addAttribute("totalTransacoesSuspeitas", transacoes.size());
        model.addAttribute("totalContasSuspeitas", contasOrigem.size() + contasDestino.size());

        return "analise";
    }
}
