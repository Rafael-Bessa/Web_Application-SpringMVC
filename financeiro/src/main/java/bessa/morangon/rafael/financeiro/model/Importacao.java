package bessa.morangon.rafael.financeiro.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "importacoes")
public class Importacao {

    @Id @GeneratedValue
    private Long id;
    private LocalDateTime dataTransacao;
    private LocalDateTime dataAgora = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;
    public Importacao(LocalDateTime data, Usuario usuario){
        this.dataTransacao = data;
        this.usuario = usuario;
    }

}
