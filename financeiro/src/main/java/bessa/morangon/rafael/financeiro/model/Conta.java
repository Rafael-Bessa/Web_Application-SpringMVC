package bessa.morangon.rafael.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;
@AllArgsConstructor
@Getter
@Setter
public class Conta {

    private String banco;
    private String agencia;
    private String conta;

    private BigDecimal valorTransferido;

    private TipoMovimentacao movimentacao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta1 = (Conta) o;
        return Objects.equals(banco, conta1.banco) && Objects.equals(agencia, conta1.agencia) && Objects.equals(conta, conta1.conta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(banco, agencia, conta);
    }
}
