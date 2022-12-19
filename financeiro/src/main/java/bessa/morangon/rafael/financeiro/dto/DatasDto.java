package bessa.morangon.rafael.financeiro.dto;

import bessa.morangon.rafael.financeiro.model.Importacao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class DatasDto {

    private Long id;
    private String dataTransacao;
    private String dataAtual;
    private String nomeImportador;
    private LocalDateTime dataTransacaoParaFazerOrdenacaoDaLista;

    public DatasDto(Importacao i){
        this.dataAtual = formataDataImportacao(i.getDataAgora());
        this.dataTransacao = formataDataTransacao(i.getDataTransacao());
        this.nomeImportador = i.getUsuario().getNome();
        this.id = i.getId();
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
