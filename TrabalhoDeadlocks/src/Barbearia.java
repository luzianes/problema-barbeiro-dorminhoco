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
            System.out.println("Cliente " + cliente.getID() + " esperando corte...");
            notify();
            return true;
        } else {
        	System.out.println("Cliente " + cliente.getID() + " tentou entrar na barbearia, mas está lotada... indo dar uma voltinha");
            return false;
        }
    }

    public synchronized Cliente proximoCliente(int barbeiroID) {
        while (clientesEsperando == 0) {
            try {
            	System.out.println("Barbeiro " + barbeiroID + " indo dormir um pouco... não há clientes na barbearia...");
                wait();
                System.out.println("Barbeiro " + barbeiroID + " acordou! Começando os trabalhos!");
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
