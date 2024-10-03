class Barbeiro extends Pessoa {
    private Barbearia barbearia;

    public Barbeiro(int id, Barbearia barbearia) {
        super(id);
        this.barbearia = barbearia;
    }

    public void iniciar() {
        while (true) {
            Cliente cliente = barbearia.proximoCliente(id);
            if (cliente != null) {
                System.out.printf("Barbeiro %d cortando cabelo do Cliente %d\n", id, cliente.getID());
                try {
                    Thread.sleep(1000 + (int)(Math.random() * 2000)); // Simula tempo de corte entre 1 a 3 segundos
                    barbearia.corteTerminado(cliente);
                } catch (Exception e) {
                    System.out.println("Erro ao cortar cabelo.");
                }
            }
        }
    }
}