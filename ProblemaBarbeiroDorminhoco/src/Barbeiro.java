class Barbeiro extends Pessoa {
    private Barbearia barbearia;

    public Barbeiro(int id, Barbearia barbearia) {
        super(id);
        this.barbearia = barbearia;
    }

    public void iniciar() {
        while (true) {
            Cliente cliente = barbearia.proximoCliente();
            if (cliente != null) {
                System.out.println("Barbeiro " + id + " cortando cabelo do Cliente " + cliente.getID());
                try {
                    Thread.sleep(1000 + (int)(Math.random() * 2000)); // Espera entre 1 e 3 segundos
                    barbearia.corteTerminado(cliente);
                } catch (Exception e) {
                    System.out.println("Erro ao cortar cabelo.");
                }
            } else {
                System.out.println("Barbeiro " + id + " indo dormir... não há clientes na barbearia.");
            }
        }
    }
}