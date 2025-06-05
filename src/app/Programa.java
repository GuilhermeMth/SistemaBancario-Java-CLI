package app;

import java.math.BigDecimal;
import java.util.Scanner;
import bancario.Cliente;
import bancario.Conta;
import persistencia.PersistenciaArquivo;

public class Programa {
    static PersistenciaArquivo persistencia = new PersistenciaArquivo();

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n=== SISTEMA BANCÁRIO ===");
                System.out.println("1) Operações de Cliente");
                System.out.println("2) Operações de Conta (requer CPF)");
                System.out.println("0) Sair do Programa");
                int opcao = lerOpcao(sc);
                switch (opcao) {
                    case 1:
                        menuCliente(sc);
                        break;
                    case 2:
                        System.out.print("Informe o CPF do cliente: ");
                        String cpf = sc.nextLine();
                        menuConta(sc, cpf);
                        break;
                    case 0:
                        System.out.println("Programa Finalizado. Até Logo!");
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            }
        }
    }

    private static void menuCliente(Scanner sc) {
        while (true) {
            System.out.println("\n--- MENU CLIENTE ---");
            System.out.println("1) Cadastrar Cliente");
            System.out.println("2) Listar Clientes");
            System.out.println("3) Localizar Cliente por CPF");
            System.out.println("4) Remover Cliente");
            System.out.println("5) Atualizar Cliente");
            System.out.println("0) Voltar");
            int opcao = lerOpcao(sc);
            switch (opcao) {
                case 1:
                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    if (persistencia.cadastrarCliente(new Cliente(cpf, nome))) {
                        System.out.println("Cliente cadastrado com sucesso!");
                    } else {
                        System.out.println("Cliente já cadastrado.");
                    }
                    break;
                case 2:
                    System.out.println(persistencia);
                    break;
                case 3:
                    System.out.print("CPF do cliente: ");
                    Cliente encontrado = persistencia.localizarCliente(sc.nextLine());
                    if (encontrado != null) {
                        System.out.println("Cliente encontrado: " + encontrado);
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;
                case 4:
                    System.out.print("CPF do cliente: ");
                    if (persistencia.removerCliente(sc.nextLine())) {
                        System.out.println("Cliente removido com sucesso.");
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;
                case 5:
                    System.out.print("CPF do cliente a ser atualizado: ");
                    String cpfAtualizar = sc.nextLine();
                    Cliente clienteAtualizar = persistencia.localizarCliente(cpfAtualizar);
                    if (clienteAtualizar != null) {
                        System.out.print("Novo nome: ");
                        clienteAtualizar.setNome(sc.nextLine());
                        if (persistencia.atualizarCliente(clienteAtualizar)) {
                            System.out.println("Cliente atualizado com sucesso!");
                        } else {
                            System.out.println("Erro ao atualizar cliente.");
                        }
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
            pausar(sc);
        }
    }

    private static void menuConta(Scanner sc, String cpf) {
        Cliente clienteAtual = persistencia.localizarCliente(cpf);
        if (clienteAtual == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        while (true) {
            System.out.println("\n--- MENU CONTA ---");
            System.out.println("1) Criar conta");
            System.out.println("2) Listar contas");
            System.out.println("3) Remover conta");
            System.out.println("4) Depositar");
            System.out.println("5) Sacar");
            System.out.println("6) Transferir");
            System.out.println("7) Consultar saldo");
            System.out.println("8) Consultar balanço total");
            System.out.println("0) Voltar");
            int opcao = lerOpcao(sc);
            switch (opcao) {
                case 1:
                    System.out.print("Número da conta: ");
                    String numero = sc.nextLine();
                    Conta novaConta = new Conta(numero);
                    if (clienteAtual.adicionarConta(novaConta)) {
                        persistencia.atualizarCliente(clienteAtual);
                        System.out.println("Conta criada com sucesso!");
                    } else {
                        System.out.println("Conta já existe.");
                    }
                    break;
                case 2:
                    System.out.println(clienteAtual);
                    break;
                case 3:
                    System.out.print("Número da conta: ");
                    if (clienteAtual.removerConta(sc.nextLine())) {
                        persistencia.atualizarCliente(clienteAtual);
                        System.out.println("Conta removida com sucesso!");
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;
                case 4:
                    System.out.print("Número da conta: ");
                    Conta contaDep = clienteAtual.localizarConta(sc.nextLine());
                    if (contaDep != null) {
                        System.out.print("Quantia a depositar: ");
                        BigDecimal quantiaDep = lerBigDecimal(sc);
                        if (contaDep.realizarDeposito(quantiaDep)) {
                            persistencia.atualizarCliente(clienteAtual);
                            System.out.println("Depósito realizado.");
                        } else {
                            System.out.println("Depósito não realizado. Valor inválido.");
                        }
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;
                case 5:
                    System.out.print("Número da conta: ");
                    Conta contaSaq = clienteAtual.localizarConta(sc.nextLine());
                    if (contaSaq != null) {
                        System.out.print("Quantia a sacar: ");
                        BigDecimal quantiaSaq = lerBigDecimal(sc);
                        if (contaSaq.realizarSaque(quantiaSaq)) {
                            persistencia.atualizarCliente(clienteAtual);
                            System.out.println("Saque realizado.");
                        } else {
                            System.out.println("Saque não realizado. Valor inválido ou saldo insuficiente.");
                        }
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;
                case 6:
                    System.out.print("Sua conta (número): ");
                    Conta contaOrigem = clienteAtual.localizarConta(sc.nextLine());
                    if (contaOrigem == null) {
                        System.out.println("Conta de origem não encontrada.");
                        break;
                    }
                    System.out.print("CPF do destinatário: ");
                    Cliente clienteDestino = persistencia.localizarCliente(sc.nextLine());
                    if (clienteDestino == null) {
                        System.out.println("Cliente destino não encontrado.");
                        break;
                    }
                    System.out.print("Conta destino (número): ");
                    Conta contaDest = clienteDestino.localizarConta(sc.nextLine());
                    if (contaDest == null) {
                        System.out.println("Conta destino não encontrada.");
                        break;
                    }
                    System.out.print("Quantia a transferir: ");
                    BigDecimal quantiaTransf = lerBigDecimal(sc);
                    if (contaOrigem.realizarTransferencia(contaDest, quantiaTransf)) {
                        persistencia.atualizarCliente(clienteAtual);
                        persistencia.atualizarCliente(clienteDestino);
                        System.out.println("Transferência realizada.");
                    } else {
                        System.out.println("Transferência não realizada. Valor inválido ou saldo insuficiente.");
                    }
                    break;
                case 7:
                    System.out.print("Número da conta: ");
                    Conta contaSaldo = clienteAtual.localizarConta(sc.nextLine());
                    if (contaSaldo != null) {
                        System.out.println("Saldo: R$" + contaSaldo.getSaldo());
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;
                case 8:
                    System.out.println("Balanço total: R$" + clienteAtual.balancoTotal());
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
            pausar(sc);
        }
    }

    private static int lerOpcao(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Digite um número válido: ");
            sc.next();
        }
        int opcao = sc.nextInt();
        sc.nextLine();
        return opcao;
    }

    private static BigDecimal lerBigDecimal(Scanner sc) {
        while (!sc.hasNextBigDecimal()) {
            System.out.print("Digite um valor válido: ");
            sc.next();
        }
        BigDecimal valor = sc.nextBigDecimal();
        sc.nextLine();
        return valor;
    }

    private static void pausar(Scanner sc) {
        System.out.println("\nPressione Enter para continuar...");
        sc.nextLine();
    }
}