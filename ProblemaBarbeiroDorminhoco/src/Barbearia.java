class Barbearia {
    private final int lugaresDisponiveis;
    private int clientesEsperando = 0;
    private Cliente[] filaDeEspera;
    private int entrada = 0;
    private int saida = 0;

    public Barbearia(int lugaresDisponiveis) {
        this.lugaresDisponiveis = lugaresDisponiveis;
        this.filaDeEspera = new Cliente[lugaresDisponiveis];
    }

    public synchronized boolean cortaCabelo(Cliente cliente) {
        if (clientesEsperando < lugaresDisponiveis) {
            filaDeEspera[entrada] = cliente;
            entrada = (entrada + 1) % lugaresDisponiveis;
            clientesEsperando++;
            notify();
            System.out.printf("Cliente %d esperando corte...\n", cliente.getID());
            return true;
        } else {
            System.out.printf("Cliente %d tentou entrar na barbearia, mas está lotada... indo dar uma voltinha\n", cliente.getID());
            return false;
        }
    }

    public synchronized Cliente proximoCliente(int barbeiroID) {
        while (clientesEsperando == 0) {
            try {
                System.out.printf("Barbeiro %d indo dormir um pouco... não há clientes na barbearia...\n", barbeiroID);
                wait();
                System.out.printf("Barbeiro %d acordou! Começando os trabalhos!\n", barbeiroID);
            } catch (InterruptedException e) {
                System.out.println("Erro ao aguardar cliente.");
            }
        }
        Cliente cliente = filaDeEspera[saida];
        filaDeEspera[saida] = null;
        saida = (saida + 1) % lugaresDisponiveis;
        clientesEsperando--;
        return cliente;
    }

    public synchronized void corteTerminado(Cliente cliente) {
        System.out.printf("Cliente %d terminou o corte… saindo da barbearia!\n", cliente.getID());
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

        for (int i = 1; i <= numBarbeiros; i++) {
            Barbeiro barbeiro = new Barbeiro(i, barbearia);
            new Thread(() -> barbeiro.iniciar()).start();
        }

        for (int i = 1; i <= numClientes; i++) {
            Cliente cliente = new Cliente(i, barbearia);
            new Thread(() -> cliente.iniciar()).start();
        }
    }
}
