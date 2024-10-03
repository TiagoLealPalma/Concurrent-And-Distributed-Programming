package Week_3.Exercise2;

import java.util.Queue;

public class UnprotectedQueue extends AbstractQueue {

    private int[] array;
    private int first;
    private int last;
    private int size;

    public UnprotectedQueue(int length){
        size = 0;
        first = 0;
        last = -1;
        array = new int[length];
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public int peek(){
        if(size == 0)
            throw new IllegalStateException();
        return array[first];
    }

    public int pool(){
        if(size == 0)
            throw new IllegalStateException();
        // Resultado para devolver
        int result = array[first];

        // Puxar todos os numeros um indice para a direita
        for (int i = 1; i < size-1; i++) {
            array[i-1] = array[i];
        }
        last--;
        size--;
        return result;
    }

    public void offer(int data){
        if(size == array.length)
            throw new IllegalStateException();
        last++;
        size++;
        array[last] = data;
    }

    public int howMany(int numberToSearch){
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == numberToSearch)
                result++;
        }
        return result;
    }
}
