package persistencia;

import java.io.*;
import java.util.ArrayList;
import bancario.Cliente;

public class PersistenciaArquivo {

    private ArrayList<Cliente> clientesCadastrados = new ArrayList<>();

    public PersistenciaArquivo() {
        carregarArquivo();
    }

    public boolean cadastrarCliente(Cliente c) {
        if (!clientesCadastrados.contains(c)) {
            clientesCadastrados.add(c);
            salvarArquivo();
            return true;
        } else {
            return false;
        }
    }

    public boolean removerCliente(String cpf) {
        Cliente c = localizarCliente(cpf);
        if (c != null) {
            clientesCadastrados.remove(c);
            salvarArquivo();
            return true;
        } else {
            return false;
        }
    }

    public Cliente localizarCliente(String cpf) {
        final Cliente[] resultado = {null};
        clientesCadastrados.forEach(cliente -> {
            if (cliente.getCpf().equals(cpf)) {
                resultado[0] = cliente;
            }
        });
        return resultado[0];
    }

    public boolean atualizarCliente(Cliente c) {
        for (int i = 0; i < clientesCadastrados.size(); i++) {
            if (clientesCadastrados.get(i).getCpf().equals(c.getCpf())) {
                clientesCadastrados.set(i, c);
                salvarArquivo();
                return true;
            }
        }
        return false;
    }

    private void salvarArquivo() {
        try {
            FileOutputStream fos = new FileOutputStream("dados");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(clientesCadastrados);
            oos.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo!");
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarArquivo() {
        try {
            FileInputStream fis = new FileInputStream("dados");
            ObjectInputStream ois = new ObjectInputStream(fis);
            clientesCadastrados = (ArrayList<Cliente>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            clientesCadastrados = new ArrayList<>();
        }
    }

    public String toString() {
        if (clientesCadastrados.isEmpty()) {
            return "Nenhum Cliente encontrado";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("--- LISTA DE CLIENTES ---\n");
        clientesCadastrados.forEach(cliente -> sb.append(cliente).append("\n"));
        sb.append("-------------------------");
        return sb.toString();
    }
}