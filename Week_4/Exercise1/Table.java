package Week_4.Exercise1;

public class Table {
    private class Javali{
        private String cookedBy;

        public Javali(String cookedBy){
            this.cookedBy = cookedBy;
        }
        public String getCookedBy() {return cookedBy;}
    }
    private Javali[] table;
    private int javalisCooked = 0;
    private int javalisOnTable = 0;

    public Table(int tableCapacity){
        table = new Javali[tableCapacity];
    }

    // Meter a comida na mesa
    public synchronized void setJavaliOnTable(String cookedBy){

        while(javalisOnTable >= table.length){  // Enquanto a mesa estiver cheia
            try {
                wait();                         // A thread que entrou no metodo deve ficar a espera
                                                // que seja notificada (notifyAll), para voltar a verificar
                                                // a condicao do while()
            } catch (InterruptedException e){
            }
        }
        table[javalisOnTable] = new Javali(cookedBy);
        javalisOnTable++;
        javalisCooked++;
        drawTable();
        notifyAll();    // Notifica as threads em espera
    }

    // Comer comida na mesa
    public synchronized void eatJavali(){
        while(javalisOnTable == 0){             // Enquanto a mesa não tiver comida
            try{
                wait();                         // A thread que entrou no metodo deve ficar a espera
                                                // que seja notificada (notifyAll), para voltar a verificar
                                                // a condicao do while()
            }catch( InterruptedException e){
            }
        }
        javalisOnTable--;
        table[javalisOnTable] = null;
        drawTable();
        notifyAll();  // Notifica as threads em espera
    }

    private void drawTable(){
        String toDraw = "|";
        for(int i = 0; i < table.length; i++){
            if(table[i] != null)
                toDraw += "🍝";
            else
                toDraw += "🍽";
        }
        toDraw += "|";
        System.out.println(toDraw);
    }


    public static void main(String[] args) {
        Table table = new Table(10);
        Cook[] cooks = new Cook[5];
        Eater[] eaters = new Eater[5];

        for (int i = 0; i < 5; i++) {
            cooks[i] = new Cook(table);
            cooks[i].start();
            eaters[i] = new Eater(table);
            eaters[i].start();
        }

        try{
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 5; i++) {
            cooks[i].finish();
            eaters[i].finish();
        }

        for (int i = 0; i < 5; i++) {
             try {
                 cooks[i].join();             
                 eaters[i].join();
             }catch (InterruptedException e){
                 e.printStackTrace();
             }
        }
    }
}

/* Em relacao a versão 3 do enunciado, usando o notify em vez do notifyAll, que notifica apenas uma das
 threads, sem nenhum indicador especifico, o problema que poderia surgir era:

        - Cozinheiro 1 entra no metodo e mete um javali no prato;
        - Cozinheiro 2 entra no metodo ve que está cheio e fica no wait();
        - Cozinheiro 1 entra no metodo ve que esta cheio e fica no wait();
        - Comedor 1 entra no metodo e come o prato notificando apenas o cozinheiro 2;
        - Cozinheiro 2 corre o restante metodo e mete um javali no prato e notifica apenas o cozinheiro 1;
        - Cozinheiro 1 ve que está cheio e permanece no wait, sem notificar mais ninguem;

Acontece um bloqueio total do programa porque todas as threads estão a espera de serem
notificadas e nenhuma thread esta a correr codigo para notificar. */
