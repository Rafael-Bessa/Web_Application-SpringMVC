package bessa.morangon.rafael.financeiro.dto;

import bessa.morangon.rafael.financeiro.model.Importacao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class DatasDto {

    private Long id;
    private String dataTransacao;
    private String dataImportacao;
    private String nomeUsuario;
    private String dataTransacaoParaUrl;
    private LocalDateTime dataTransacaoParaFazerOrdenacaoDaLista;

    public DatasDto(Importacao i){
        this.id = i.getId();  // IMPORTANTE: ID para o link de detalhes
        this.dataTransacao = formataDataTransacao(i.getDataTransacao());
        this.dataImportacao = formataDataImportacao(i.getDataAgora());
        this.nomeUsuario = i.getUsuario().getNome();
        this.dataTransacaoParaUrl = i.getDataTransacao().toString();
        this.dataTransacaoParaFazerOrdenacaoDaLista = i.getDataTransacao();
    }

    private String formataDataTransacao(LocalDateTime data){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

    private String formataDataImportacao(LocalDateTime data){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        return data.format(formatter);
    }
}
