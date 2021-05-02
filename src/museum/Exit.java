package museum;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Exit implements Runnable {
    String name;
    int num_turnstiles;
    int current_turnstile_open;
    Queue<String> queue = new LinkedList<>();
    final Lock lock = new ReentrantLock();
    final Condition Turnstile_Full = lock.newCondition();


    public Exit(String name, int num_turnstiles) {
        this.name = name;
        this.num_turnstiles = num_turnstiles;
        this.current_turnstile_open = num_turnstiles;
    }

    public void LetVisitorExit() throws InterruptedException {
        //If the open turnstile is full
        //Turnstile condition will await (waiting for signal)
        while(true){
            if(Museum.current_size == 0 && Museum.opened == false){
                break;
            }
            if(current_turnstile_open == 0){
                Turnstile_Full.await();
            }
            //Visitor getting into the turnstile
            current_turnstile_open--;
            Thread.sleep(2000);
            //Visitor got through the turnstile
            current_turnstile_open++;
            Turnstile_Full.signalAll();
            //Visitor exit the museum
            Museum.current_size--;
        }

    }

    @Override
    public void run() {
        try {
            LetVisitorExit();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
