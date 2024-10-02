public class Barbearia {
    private final int lugaresDisponiveis;
    private int clientesEsperando = 0;

    public Barbearia(int lugaresDisponiveis) {
        this.lugaresDisponiveis = lugaresDisponiveis;
    }

    // Método para o cliente tentar cortar o cabelo
    public synchronized boolean cortaCabelo(Cliente cliente) {
        if (clientesEsperando < lugaresDisponiveis) {
            clientesEsperando++;
            notifyAll();
            System.out.println("Cliente " + cliente.getID() + " esperando corte...");
            return true;
        } else {
            return false;
        }
    }

    // Método para o barbeiro pegar o próximo cliente
    public synchronized Cliente proximoCliente() {
        while (clientesEsperando == 0) {
            try {
                wait();
            } catch (Exception e) {
                System.out.println("Erro ao aguardar cliente.");
            }
        }
        if (clientesEsperando > 0) {
            clientesEsperando--;
            return new Cliente(0, this); // Apenas para exemplificação, você pode mudar a lógica
        }
        return null;
    }

    // Método para indicar que o corte foi terminado
    public synchronized void corteTerminado(Cliente cliente) {
        System.out.println("Cliente " + cliente.getID() + " terminou o corte… saindo da barbearia!");
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Por favor, insira os parâmetros: <número de barbeiros> <número de cadeiras> <número de clientes>");
            return;
        }

        int numBarbeiros = Integer.parseInt(args[0]);
        int numCadeiras = Integer.parseInt(args[1]);
        int numClientes = Integer.parseInt(args[2]);

        Barbearia barbearia = new Barbearia(numCadeiras);

        // Criar e iniciar os barbeiros
        for (int i = 1; i <= numBarbeiros; i++) {
            Barbeiro barbeiro = new Barbeiro(i, barbearia);
            new Thread(() -> barbeiro.iniciar()).start();
        }

        // Criar e iniciar os clientes
        for (int i = 1; i <= numClientes; i++) {
            Cliente cliente = new Cliente(i, barbearia);
            new Thread(() -> cliente.iniciar()).start();
        }
    }
}