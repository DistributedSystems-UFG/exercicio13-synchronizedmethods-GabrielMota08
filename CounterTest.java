class SynchronizedCounter {
    private int c = 0;

    public synchronized void increment() {
        c++;
    }

    public synchronized void decrement() {
        c--;
    }

    public synchronized int value() {
        return c;
    }
}

public class CounterTest {

    static class Counter {

        private int c = 0;

        public void increment() {
            c++;
        }

        public void decrement() {
            c--;
        }

        public int value() {
            return c;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int iteracoes = 100000;

        System.out.println("Sem Sincronização");
        Counter unsafeCounter = new Counter();
        
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < iteracoes; i++) unsafeCounter.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < iteracoes; i++) unsafeCounter.increment();
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Valor esperado: " + (iteracoes * 2));
        System.out.println("Valor obtido:   " + unsafeCounter.value());


        System.out.println("\nTeste com sincronização");
        SynchronizedCounter safeCounter = new SynchronizedCounter();

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < iteracoes; i++) safeCounter.increment();
        });
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < iteracoes; i++) safeCounter.increment();
        });

        t3.start();
        t4.start();
        t3.join();
        t4.join();

        System.out.println("Valor esperado: " + (iteracoes * 2));
        System.out.println("Valor obtido:   " + safeCounter.value());
    }
}