class Cliente extends Pessoa {
    private Barbearia barbearia;

    public Cliente(int id, Barbearia barbearia) {
        super(id);
        this.barbearia = barbearia;
    }

    public void iniciar() {
        try {
            Thread.sleep(3000 + (int)(Math.random() * 2000)); // Espera entre 3 e 5 segundos
            if (barbearia.cortaCabelo(this)) {
                System.out.printf("Cliente %d cortando cabelo...\n", id);
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar cliente.");
        }
    }
}