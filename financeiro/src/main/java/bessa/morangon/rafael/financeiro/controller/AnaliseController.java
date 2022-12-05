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

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/analise")
public class AnaliseController {
    private AnaliseService service;

    @GetMapping
    public String telaAnalise(){
        return "analise";
    }

    @PostMapping
    public String analise(DataAnaliseDto d, Model model){

        String [] datas = d.getData().split("-");

        List<Transacao> transacoes = service.buscaTransacoesSuspeitasMes(datas[0], datas[1]);
        List<Conta> contasOrigem = service.buscaContasOrigemSuspeitasMes(datas[0], datas[1]);
        List<Conta> contasDestino = service.buscaContasDestinoSuspeitasMes(datas[0], datas[1]);

        model.addAttribute("transacoesSuspeitas", transacoes);
        model.addAttribute("contasSuspeitasOrigem", contasOrigem);
        model.addAttribute("contasSuspeitasDestino", contasDestino);

        return "analise";

    }

}
