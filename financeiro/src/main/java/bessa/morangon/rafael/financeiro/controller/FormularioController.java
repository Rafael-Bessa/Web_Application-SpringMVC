package bessa.morangon.rafael.financeiro.controller;

import bessa.morangon.rafael.financeiro.dto.DatasDto;
import bessa.morangon.rafael.financeiro.model.Importacao;
import bessa.morangon.rafael.financeiro.repository.ImportacaoRepository;
import bessa.morangon.rafael.financeiro.service.ArquivoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        List<DatasDto> listaParaExibir = new ArrayList<>();
        List<Importacao> importacoes = importacaoRepository.findAll();

        for (Importacao i: importacoes) {
            listaParaExibir.add(new DatasDto(i));
        }

        listaParaExibir.sort(Comparator
                .comparing(DatasDto::getDataTransacaoParaFazerOrdenacaoDaLista)
                .reversed());

        model.addAttribute("listaParaExibir", listaParaExibir);
        model.addAttribute("nomeLogado", principal.getName());

        return "formulario";
    }

    @PostMapping
    public String recebendoArquivo(@RequestParam("arquivo") MultipartFile arquivo,
                                   RedirectAttributes redirectAttributes,
                                   Principal principal) {

        // Validação 1: Arquivo vazio
        if (arquivo.isEmpty()) {
            redirectAttributes.addFlashAttribute("tipoAlerta", "erro");
            redirectAttributes.addFlashAttribute("tituloAlerta", "Arquivo Não Selecionado");
            redirectAttributes.addFlashAttribute("mensagemAlerta",
                    "Por favor, selecione um arquivo CSV para importar.");
            return "redirect:/formulario";
        }

        // Validação 2: Extensão CSV
        String nomeArquivo = arquivo.getOriginalFilename();
        if(nomeArquivo == null || !nomeArquivo.toLowerCase().endsWith(".csv")) {
            redirectAttributes.addFlashAttribute("tipoAlerta", "erro");
            redirectAttributes.addFlashAttribute("tituloAlerta", "Formato Inválido");
            redirectAttributes.addFlashAttribute("mensagemAlerta",
                    "❌ Apenas arquivos CSV são aceitos. Arquivo: " + nomeArquivo);
            return "redirect:/formulario";
        }

        // Validação 3: Tamanho máximo
        if(arquivo.getSize() > 10 * 1024 * 1024) {
            redirectAttributes.addFlashAttribute("tipoAlerta", "erro");
            redirectAttributes.addFlashAttribute("tituloAlerta", "Arquivo Muito Grande");
            redirectAttributes.addFlashAttribute("mensagemAlerta",
                    "⚠️ Tamanho máximo: 10MB");
            return "redirect:/formulario";
        }

        try {
            // Processar arquivo
            ArquivoService.ResultadoValidacao resultado =
                    arquivoService.processarArquivoUpload(arquivo, principal);

            if(!resultado.isSucesso()) {
                // ARQUIVO REJEITADO
                redirectAttributes.addFlashAttribute("tipoAlerta", "erro");
                redirectAttributes.addFlashAttribute("tituloAlerta", "❌ Arquivo Rejeitado!");
                redirectAttributes.addFlashAttribute("mensagemAlerta",
                        String.format("🚫 O arquivo foi rejeitado devido a %d erro(s). " +
                                        "Nenhuma transação foi importada.",
                                resultado.getErros().size()));
                redirectAttributes.addFlashAttribute("listaErros", resultado.getErros());
            } else {
                // SUCESSO
                redirectAttributes.addFlashAttribute("tipoAlerta", "sucesso");
                redirectAttributes.addFlashAttribute("tituloAlerta", "✅ Importação Concluída!");
                redirectAttributes.addFlashAttribute("mensagemAlerta",
                        String.format("🎉 %d transação(ões) importadas com sucesso!",
                                resultado.getTotalLinhas()));
            }

        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("tipoAlerta", "erro");
            redirectAttributes.addFlashAttribute("tituloAlerta", "💥 Erro no Sistema");
            redirectAttributes.addFlashAttribute("mensagemAlerta",
                    "Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }

        return "redirect:/formulario";
    }
}
