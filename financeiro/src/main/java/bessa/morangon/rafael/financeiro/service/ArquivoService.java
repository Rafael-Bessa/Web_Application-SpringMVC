package bessa.morangon.rafael.financeiro.service;

import bessa.morangon.rafael.financeiro.exceptionHandler.DataRegraNegocioException;
import bessa.morangon.rafael.financeiro.model.ArquivoCSV;
import bessa.morangon.rafael.financeiro.model.Importacao;
import bessa.morangon.rafael.financeiro.model.Transacao;
import bessa.morangon.rafael.financeiro.model.Usuario;
import bessa.morangon.rafael.financeiro.repository.ImportacaoRepository;
import bessa.morangon.rafael.financeiro.repository.TransacaoRepository;
import bessa.morangon.rafael.financeiro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class ArquivoService {
    @Autowired
    private TransacaoRepository transacaoRepository;
    @Autowired
   private ImportacaoRepository importacaoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    public static LocalDateTime dataReferencia;

    public void leArquivoCSVsalvaNoBancoDeDados(ArquivoCSV arquivoRecebido, Principal p) throws Exception {

        int i = 0;
        determinaDataParaRegraDeNegocio(arquivoRecebido);

        if(verificaSeJaExisteEssaDataCadastradaNoBanco(dataReferencia)){

            Scanner scanner = new Scanner(arquivoRecebido.getArquivo());
            while(scanner.hasNext()) {
                String linha = scanner.nextLine();

                Scanner scannerParaSeparar = new Scanner(linha);
                scannerParaSeparar.useDelimiter(",");

                try {
                    i++;
                    Transacao transacao = new Transacao(scannerParaSeparar.next(), scannerParaSeparar.next(),
                            scannerParaSeparar.next(), scannerParaSeparar.next(), scannerParaSeparar.next(), scannerParaSeparar.next(),
                            new BigDecimal(scannerParaSeparar.next()), LocalDateTime.parse(scannerParaSeparar.next()));

                    if(comparaDatas(transacao.getDataTransacao(), dataReferencia)) {
                        transacaoRepository.save(transacao);

                    }

                }catch (Exception e){
                    System.out.println("Existe alguma transação inválida no arquivo .csv, existe erro na linha número: " + i);
                }
            }
            scanner.close();

        Usuario u = usuarioRepository.findByUsername(p.getName()).get();
        importacaoRepository.save(new Importacao(dataReferencia, u));

        }
    }

    private void determinaDataParaRegraDeNegocio(ArquivoCSV arquivoRecebido) throws FileNotFoundException {

        List<String> dadosPrimeiraLinha = new ArrayList<>();

        Scanner scannerPrimeiraLinha = new Scanner(arquivoRecebido.getArquivo());
        String primeiraLinha = scannerPrimeiraLinha.nextLine();

        Scanner scannerParaSeparar = new Scanner(primeiraLinha);
        scannerParaSeparar.useDelimiter(",");

        while(scannerParaSeparar.hasNext()){
            dadosPrimeiraLinha.add(scannerParaSeparar.next());
        }
        dataReferencia = LocalDateTime.parse(dadosPrimeiraLinha.get(dadosPrimeiraLinha.size() - 1));
    }
    private boolean verificaSeJaExisteEssaDataCadastradaNoBanco(LocalDateTime data) throws Exception {
        Optional<Transacao> byDataTransacao = transacaoRepository.findByDataTransacao(data);
        if(byDataTransacao.isPresent()){
            throw new DataRegraNegocioException();
        }
        return true;
    }
    private boolean comparaDatas(LocalDateTime primeira, LocalDateTime segunda){
        return primeira.getYear() == segunda.getYear() && primeira.getMonthValue() == segunda.getMonthValue()
                && primeira.getDayOfMonth() == segunda.getDayOfMonth();
    }

}
