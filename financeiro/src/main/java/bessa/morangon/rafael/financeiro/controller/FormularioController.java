package bessa.morangon.rafael.financeiro.controller;

import bessa.morangon.rafael.financeiro.dto.DatasDto;
import bessa.morangon.rafael.financeiro.model.ArquivoCSV;
import bessa.morangon.rafael.financeiro.model.Importacao;
import bessa.morangon.rafael.financeiro.repository.ImportacaoRepository;
import bessa.morangon.rafael.financeiro.service.ArquivoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/formulario")
public class FormularioController {
    private ImportacaoRepository importacaoRepository;
    private ArquivoService arquivoService;

    @GetMapping
    public String formulario(Model model, Principal principal){

        String detalhe = "Ver Detalhes";
        List<DatasDto> listaParaExibir = new ArrayList<>();
        List<Importacao> importacoes = importacaoRepository.findAll();
        for (Importacao i: importacoes) {
            listaParaExibir.add(new DatasDto(i));
        }

        listaParaExibir.sort(Comparator.comparing(DatasDto::getDataTransacaoParaFazerOrdenacaoDaLista).reversed());

        model.addAttribute("listaParaExibir", listaParaExibir);
        model.addAttribute("nomeLogado", principal.getName());
        model.addAttribute("detalhe", detalhe);
        return "formulario";
    }

    @PostMapping
    public String recebendoArquivo(ArquivoCSV arquivo, Model model, Principal principal) throws Exception {
        arquivoService.leArquivoCSVsalvaNoBancoDeDados(arquivo, principal);
        return "redirect:formulario";
    }

}
