class Barbeiro extends Pessoa {
    private Barbearia barbearia;

    public Barbeiro(int id, Barbearia barbearia) {
        super(id);
        this.barbearia = barbearia;
    }

    public void iniciar() {
        while (true) {
            Cliente cliente = barbearia.proximoCliente(id);  // Barbeiro pega o próximo cliente da fila
            if (cliente != null) {
                // Aqui o barbeiro realmente começou a cortar o cabelo
                System.out.printf("Cliente %d cortando cabelo...\n", cliente.getID());  // Mensagem de corte do cliente
                System.out.printf("Barbeiro %d cortando cabelo do Cliente %d\n", id, cliente.getID());
                try {
                    Thread.sleep(1000 + (int)(Math.random() * 2000));  // Simula tempo de corte entre 1 a 3 segundos
                    barbearia.corteTerminado(cliente);
                } catch (Exception e) {
                    System.out.println("Erro ao cortar cabelo.");
                }
            }
        }
    }
}
