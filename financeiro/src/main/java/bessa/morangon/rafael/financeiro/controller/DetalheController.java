package bessa.morangon.rafael.financeiro.controller;

import bessa.morangon.rafael.financeiro.dto.DatasDto;
import bessa.morangon.rafael.financeiro.model.Importacao;
import bessa.morangon.rafael.financeiro.model.Transacao;
import bessa.morangon.rafael.financeiro.repository.ImportacaoRepository;
import bessa.morangon.rafael.financeiro.repository.TransacaoRepository;
import bessa.morangon.rafael.financeiro.service.TransacaoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/detalhes")
public class DetalheController {

    private TransacaoService service;
    private ImportacaoRepository repository;
    private TransacaoRepository transacaoRepository;

    @GetMapping("/{id}")
    public String detalhar(@PathVariable Long id, Model model){

        Optional<Importacao> imp = repository.findById(id);
        DatasDto dados = new DatasDto(imp.get());

        List<Transacao> transacoes = service.buscaTodasTransacoesNaMesmaData(imp.get().getDataTransacao());

        model.addAttribute("importacao", dados);
        model.addAttribute("lista", transacoes);

        return "detalhe";
    }


}
