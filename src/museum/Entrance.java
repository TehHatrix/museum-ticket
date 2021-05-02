package museum;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Entrance implements Runnable {
    String name;
    int num_turnstiles;
    int current_turnstile_open;
    Queue<String> queue = new LinkedList<>();
    final Lock lock = new ReentrantLock();
    final Condition Turnstile_Full = lock.newCondition();


    public Entrance(String name, int num_turnstiles) {
        this.name = name;
        this.num_turnstiles = num_turnstiles;
        this.current_turnstile_open = num_turnstiles;
    }


    public void LetVisitorEnter() throws InterruptedException {
        //While Museum Open
            //If the open turnstile not full
            //Visitor enter the museum
        while(Museum.opened){
            //If the open turnstile is full
            //Turnstile condition will await (waiting for signal)
            if(Museum.total_enter == Museum.MAX_SIZE){
                System.out.println("Total Enter is 900. Museum is closing!");
                Museum.close();
                break;
            }
            if(current_turnstile_open == 0 || Museum.current_size < 100){
                Turnstile_Full.await();
            }
//            System.out.println("TicketID : " + ticketID + " getting into turnstile");
            //Visitor getting into the turnstile
            current_turnstile_open--;
            Thread.sleep(2000);
            //Visitor got through the turnstile
            current_turnstile_open++;
            Turnstile_Full.signalAll();
//            System.out.println("TicketID : " + ticketID + " got through the turnstile");
            //Visitor enter the museum
            Museum.current_size++;
            Museum.total_enter++;
            //visitor stay at museum
            VisitorControl.stayMuseum();
        }
    }


    @Override
    public void run() {
        try {
            LetVisitorEnter();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
