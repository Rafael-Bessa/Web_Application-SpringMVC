package bessa.morangon.rafael.financeiro.controller;

import bessa.morangon.rafael.financeiro.dto.DatasDto;
import bessa.morangon.rafael.financeiro.model.Importacao;
import bessa.morangon.rafael.financeiro.model.Transacao;
import bessa.morangon.rafael.financeiro.repository.ImportacaoRepository;
import bessa.morangon.rafael.financeiro.service.TransacaoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/detalhes")
public class DetalheController {

    private TransacaoService service;
    private ImportacaoRepository repository;

    @GetMapping("/{id}")
    public String detalhar(@PathVariable Long id,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        // Buscar importação pelo ID
        Optional<Importacao> importacaoOpt = repository.findById(id);

        // Verificar se existe
        if(!importacaoOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("tipoAlerta", "erro");
            redirectAttributes.addFlashAttribute("tituloAlerta", "Importação Não Encontrada");
            redirectAttributes.addFlashAttribute("mensagemAlerta",
                    "A importação solicitada não foi encontrada no sistema.");
            return "redirect:/formulario";
        }

        Importacao importacao = importacaoOpt.get();
        DatasDto dados = new DatasDto(importacao);

        // Buscar transações da mesma data
        List<Transacao> transacoes = service.buscaTodasTransacoesNaMesmaData(
                importacao.getDataTransacao()
        );

        model.addAttribute("importacao", dados);
        model.addAttribute("lista", transacoes);
        model.addAttribute("totalTransacoes", transacoes.size());

        return "detalhe";
    }
}
