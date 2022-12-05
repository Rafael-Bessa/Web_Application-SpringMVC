package bessa.morangon.rafael.financeiro.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "transacoes")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String bancoOrigem;
    @NotBlank
    private String agenciaOrigem;
    @NotBlank
    private String contaOrigem;
    @NotBlank
    private String bancoDestino;
    @NotBlank
    private String agenciaDestino;
    @NotBlank
    private String contaDestino;
    @NotNull @Positive
    private BigDecimal valorTransacao;
    @NotNull 
    private LocalDateTime dataTransacao;

    public Transacao(String bancoOrigem, String agenciaOrigem, String contaOrigem, String bancoDestino, String agenciaDestino, String contaDestino, BigDecimal valorTransacao, LocalDateTime dataTransacao) {
        this.bancoOrigem = bancoOrigem;
        this.agenciaOrigem = agenciaOrigem;
        this.contaOrigem = contaOrigem;
        this.bancoDestino = bancoDestino;
        this.agenciaDestino = agenciaDestino;
        this.contaDestino = contaDestino;
        this.valorTransacao = valorTransacao;
        this.dataTransacao = dataTransacao;
    }

}
