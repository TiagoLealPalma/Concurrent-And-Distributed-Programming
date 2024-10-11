package Week_4.Extra2;

import Week_4.Exercise3.Cliente;

public class Barber extends Thread{
    private volatile boolean running = true;
    private BarberShop barberShop;

    public Barber(BarberShop barberShop) {
        this.barberShop = barberShop;
    }

    @Override
    public void run() {
        while(running) {
            Client client = barberShop.getClient(); // Vai buscar um cliente
            if(client == null){ // Se for null, quer dizer que foi interrompido em quanto esperava por um cliente
                System.out.println("Barbeiro foi interrompido durante a espera.");
                return;
            }
            // Se não, começa a passar régua
            System.out.println("Barbeiro: A cortar o cabelo do cliente nº" + client.getClientId());
            try {
                sleep(1000); // Simula o tempo da régua
            } catch (InterruptedException e) {
                System.out.println("Barbeiro foi interrompido durante o pente.");
                return;
            }
            // Cabelo cortado sinaliza à thread client para que possa terminar o seu processo.
            System.out.println("Régua passada");
        }
        System.out.println("Serviço acabou.");
    }

    public void stopRunning(){
        running = false;
        interrupt();
    }
}
