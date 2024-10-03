package Week_3.Exercise2;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicQueue extends AbstractQueue {

        private AtomicIntegerArray array;
        private AtomicInteger first;
        private AtomicInteger last;
        private AtomicInteger size;

        public AtomicQueue(int length){
            size = new AtomicInteger(0);
            first = new AtomicInteger(0);
            last = new AtomicInteger(-1);
            array = new AtomicIntegerArray(length);
        }

        public boolean isEmpty(){
            return size.get() == 0;
        }

        public int size(){
            return size.get();
        }

        public int peek(){
            if(size.get() == 0)
                throw new IllegalStateException();
            return array.get(first.get());
        }

        public int pool(){
            if(size.get() == 0)
                throw new IllegalStateException();
            // Resultado para devolver
            int result = array.get(first.get());

            // Puxar todos os numeros um indice para a direita
            for (int i = 1; i < size.get()-1; i++) {
                //array[i-1] = array[i];
                array.getAndSet(i-1, i);
            }
            last.decrementAndGet();
            size.decrementAndGet();
            return result;
        }

        public void offer(int data){
            if(size.get() == array.length())
                throw new IllegalStateException();
            last.incrementAndGet();
            size.incrementAndGet();
            //array[last] = data;
            array.set(last.get(), data);
        }

        public int howMany(int numberToSearch){
            int result = 0;
            for (int i = 0; i < array.length(); i++) {
                if(array.get(i) == numberToSearch)
                    result++;
            }
            return result;
        }
    }