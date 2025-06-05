package bancario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Conta implements Serializable {
    private static final long serialVersionUID = 1L;

    private String numero;
    private BigDecimal saldo;
    private LocalDateTime dataAbertura;
    private boolean status;

    public Conta() {
    }

    public Conta(String numero) {
        this.numero = numero;
        this.saldo = BigDecimal.ZERO.setScale(2);
        this.dataAbertura = LocalDateTime.now();
        this.status = true;
    }

    public boolean realizarSaque(BigDecimal quantia) {
        if (status && quantia.compareTo(BigDecimal.ZERO) > 0 && quantia.compareTo(this.saldo) <= 0) {
            saldo = saldo.subtract(quantia);
            return true;
        } else {
            return false;
        }
    }

    public boolean realizarDeposito(BigDecimal quantia) {
        if (status && quantia.compareTo(BigDecimal.ZERO) > 0) {
            saldo = saldo.add(quantia);
            return true;
        } else {
            return false;
        }
    }

    public boolean realizarTransferencia(Conta destino, BigDecimal quantia) {
        if (status && destino.status && quantia.compareTo(BigDecimal.ZERO) > 0 && quantia.compareTo(this.saldo) <= 0) {
            saldo = saldo.subtract(quantia);
            destino.saldo = destino.saldo.add(quantia);
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "Conta " + numero + " - saldo: R$" + saldo;
    }

    public int hashCode() {
        return Objects.hash(numero);
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Conta other = (Conta) obj;
        return Objects.equals(numero, other.numero);
    }

    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public BigDecimal getSaldo() {
        return saldo;
    }
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }
    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}