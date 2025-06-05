package bancario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cpf;
    private String nome;
    private ArrayList<Conta> contas;

    public Cliente() {
        this.contas = new ArrayList<>();
    }

    public Cliente(String cpf) {
        this.cpf = cpf;
        this.contas = new ArrayList<>();
    }

    public Cliente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        this.contas = new ArrayList<>();
    }

    public boolean adicionarConta(Conta c) {
        if (localizarConta(c.getNumero()) == null) {
            contas.add(c);
            return true;
        } else {
            return false;
        }
    }

    public boolean removerConta(String numero) {
        Conta c = localizarConta(numero);
        if (c != null) {
            contas.remove(c);
            return true;
        } else {
            return false;
        }
    }

    public Conta localizarConta(String numeroConta) {
        final Conta[] resultado = {null};
        contas.forEach(conta -> {
            if (conta.getNumero().equals(numeroConta)) {
                resultado[0] = conta;
            }
        });
        return resultado[0];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(nome).append(" | Cpf: ").append(cpf);
        sb.append("\nContas: ");
        contas.forEach(conta -> sb.append(conta).append(" "));
        return sb.toString();
    }

    public int hashCode() {
        return Objects.hash(cpf);
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Cliente other = (Cliente) obj;
        return Objects.equals(cpf, other.cpf);
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public ArrayList<Conta> getContas() {
        return contas;
    }
    public void setContas(ArrayList<Conta> contas) {
        this.contas = contas;
    }

    public BigDecimal balancoTotal() {
        final BigDecimal[] total = {BigDecimal.ZERO};
        contas.forEach(conta -> {
            total[0] = total[0].add(conta.getSaldo());
        });
        return total[0];
    }
}