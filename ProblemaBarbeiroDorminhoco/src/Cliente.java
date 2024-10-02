class Cliente extends Pessoa {
    private Barbearia barbearia;

    public Cliente(int id, Barbearia barbearia) {
        super(id);
        this.barbearia = barbearia;
    }

    public void iniciar() {
        try {
            Thread.sleep(3000); // Espera entre 3 e 5 segundos antes de tentar cortar o cabelo
            if (barbearia.cortaCabelo(this)) {
                System.out.println("Cliente " + id + " cortando cabelo...");
            } else {
                System.out.println("Cliente " + id + " tentou entrar na barbearia, mas está lotada... indo dar uma voltinha");
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar cliente.");
        }
    }
}