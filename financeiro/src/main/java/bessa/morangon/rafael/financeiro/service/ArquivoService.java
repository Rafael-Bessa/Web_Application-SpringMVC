package bessa.morangon.rafael.financeiro.service;

import bessa.morangon.rafael.financeiro.model.Importacao;
import bessa.morangon.rafael.financeiro.model.Transacao;
import bessa.morangon.rafael.financeiro.model.Usuario;
import bessa.morangon.rafael.financeiro.repository.ImportacaoRepository;
import bessa.morangon.rafael.financeiro.repository.TransacaoRepository;
import bessa.morangon.rafael.financeiro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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

    private LocalDateTime dataReferencia;

    /**
     * Classe interna para resultado da validação
     */
    public static class ResultadoValidacao {
        private final boolean sucesso;
        private final List<String> erros;
        private final int totalLinhas;

        public ResultadoValidacao(boolean sucesso, List<String> erros, int totalLinhas) {
            this.sucesso = sucesso;
            this.erros = erros;
            this.totalLinhas = totalLinhas;
        }

        public boolean isSucesso() { return sucesso; }
        public List<String> getErros() { return erros; }
        public int getTotalLinhas() { return totalLinhas; }
    }

    /**
     * Processa arquivo CSV com validação completa ANTES de salvar
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultadoValidacao processarArquivoUpload(MultipartFile arquivo, Principal p) throws Exception {
        if (arquivo.isEmpty()) {
            throw new Exception("Arquivo vazio!");
        }

        List<String> erros = new ArrayList<>();
        List<Transacao> transacoesValidadas = new ArrayList<>();
        int numeroLinha = 0;

        // ETAPA 1: Determinar data de referência
        InputStream inputStream = arquivo.getInputStream();
        try {
            determinaDataParaRegraDeNegocioComInputStream(inputStream);
        } catch (Exception e) {
            return new ResultadoValidacao(false,
                    List.of("❌ Erro ao ler data de referência: " + e.getMessage()), 0);
        } finally {
            inputStream.close();
        }

        // ETAPA 2: Validar se já existe importação
        if(!verificaSeJaExisteEssaDataCadastradaNoBanco(dataReferencia)){
            return new ResultadoValidacao(false,
                    List.of("⚠️ Já existe importação para a data: " +
                            dataReferencia.toLocalDate()), 0);
        }

        // ETAPA 3: VALIDAR TODAS AS LINHAS
        inputStream = arquivo.getInputStream();
        Scanner scanner = new Scanner(inputStream, "UTF-8");

        while(scanner.hasNext()) {
            numeroLinha++;
            String linha = scanner.nextLine().trim();

            if(linha.isEmpty()) {
                continue;
            }

            Scanner scannerParaSeparar = new Scanner(linha);
            scannerParaSeparar.useDelimiter(",");

            try {
                List<String> campos = new ArrayList<>();
                while(scannerParaSeparar.hasNext()) {
                    campos.add(scannerParaSeparar.next().trim());
                }

                // VALIDAÇÃO 1: Quantidade de campos
                if(campos.size() != 8) {
                    erros.add(String.format(
                            "❌ Linha %d: Campos incorretos. Encontrados: %d, Esperados: 8",
                            numeroLinha, campos.size()
                    ));
                    scannerParaSeparar.close();
                    continue;
                }

                // VALIDAÇÃO 2: Campos vazios
                List<String> nomesDosCampos = List.of(
                        "Banco Origem", "Agência Origem", "Conta Origem",
                        "Banco Destino", "Agência Destino", "Conta Destino",
                        "Valor", "Data/Hora"
                );

                boolean temErroNaLinha = false;
                for(int i = 0; i < campos.size(); i++) {
                    if(campos.get(i).isEmpty()) {
                        erros.add(String.format(
                                "❌ Linha %d: Campo '%s' (posição %d) está vazio",
                                numeroLinha, nomesDosCampos.get(i), i + 1
                        ));
                        temErroNaLinha = true;
                    }
                }

                if(temErroNaLinha) {
                    scannerParaSeparar.close();
                    continue;
                }

                // VALIDAÇÃO 3: Parse do valor
                String valorStr = campos.get(6);
                BigDecimal valor;
                try {
                    valor = new BigDecimal(valorStr);
                    if(valor.compareTo(BigDecimal.ZERO) <= 0) {
                        erros.add(String.format(
                                "❌ Linha %d: Valor deve ser > 0. Informado: %s",
                                numeroLinha, valorStr
                        ));
                        scannerParaSeparar.close();
                        continue;
                    }
                } catch (NumberFormatException e) {
                    erros.add(String.format(
                            "❌ Linha %d: Valor inválido '%s'",
                            numeroLinha, valorStr
                    ));
                    scannerParaSeparar.close();
                    continue;
                }

                // VALIDAÇÃO 4: Parse da data
                String dataStr = campos.get(7);
                LocalDateTime dataTransacao;
                try {
                    dataTransacao = LocalDateTime.parse(dataStr);
                } catch (DateTimeParseException e) {
                    erros.add(String.format(
                            "❌ Linha %d: Data inválida '%s'",
                            numeroLinha, dataStr
                    ));
                    scannerParaSeparar.close();
                    continue;
                }

                // Criar transação temporária
                Transacao transacao = new Transacao(
                        campos.get(0), campos.get(1), campos.get(2),
                        campos.get(3), campos.get(4), campos.get(5),
                        valor, dataTransacao
                );

                if(comparaDatas(transacao.getDataTransacao(), dataReferencia)) {
                    transacoesValidadas.add(transacao);
                }

            } catch (Exception e) {
                erros.add(String.format(
                        "❌ Linha %d: Erro inesperado - %s",
                        numeroLinha, e.getMessage()
                ));
            } finally {
                scannerParaSeparar.close();
            }
        }

        scanner.close();
        inputStream.close();

        // Se houver erro, REJEITAR tudo
        if(!erros.isEmpty()) {
            return new ResultadoValidacao(false, erros, numeroLinha);
        }

        // Salvar tudo no banco
        for(Transacao transacao : transacoesValidadas) {
            transacaoRepository.save(transacao);
        }

        Usuario u = usuarioRepository.findByUsername(p.getName())
                .orElseThrow(() -> new Exception("Usuário não encontrado"));
        importacaoRepository.save(new Importacao(dataReferencia, u));

        return new ResultadoValidacao(true, new ArrayList<>(), transacoesValidadas.size());
    }

    private void determinaDataParaRegraDeNegocioComInputStream(InputStream inputStream) throws IOException {
        Scanner scanner = new Scanner(inputStream, "UTF-8");

        if(!scanner.hasNextLine()) {
            throw new IOException("Arquivo CSV vazio");
        }

        String primeiraLinha = scanner.nextLine().trim();
        Scanner scannerCampos = new Scanner(primeiraLinha);
        scannerCampos.useDelimiter(",");

        List<String> campos = new ArrayList<>();
        while(scannerCampos.hasNext()){
            campos.add(scannerCampos.next().trim());
        }

        scannerCampos.close();

        if(campos.size() < 8) {
            throw new IOException("Primeira linha incompleta");
        }

        try {
            dataReferencia = LocalDateTime.parse(campos.get(campos.size() - 1));
        } catch (DateTimeParseException e) {
            throw new IOException("Data de referência inválida");
        }
    }

    private boolean verificaSeJaExisteEssaDataCadastradaNoBanco(LocalDateTime data) {
        Optional<Transacao> byDataTransacao = transacaoRepository.findByDataTransacao(data);
        return !byDataTransacao.isPresent();
    }

    private boolean comparaDatas(LocalDateTime primeira, LocalDateTime segunda){
        return primeira.getYear() == segunda.getYear()
                && primeira.getMonthValue() == segunda.getMonthValue()
                && primeira.getDayOfMonth() == segunda.getDayOfMonth();
    }
}
