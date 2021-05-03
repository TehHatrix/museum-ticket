package museum;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Entrance implements Runnable {
    String name;
    int num_turnstiles;
    int current_turnstile_open;
    LinkedHashMap <List<String>,Integer> queue;
    final Lock lock = new ReentrantLock();
    final Condition Turnstile_Full = lock.newCondition();


    public Entrance(String name, int num_turnstiles) {
        this.name = name;
        this.num_turnstiles = num_turnstiles;
        this.current_turnstile_open = num_turnstiles;
        this.queue = new LinkedHashMap<List<String>,Integer>();
    }

    public void EnterQueueList(int numofguest,List<String> ticket){
        queue.put(ticket,numofguest);
    }

    public void LetVisitorEnter() throws InterruptedException {
        //While Museum Open && queue is not null
        while(Museum.opened && queue != null){
            if(Museum.total_enter == Museum.MAX_SIZE){
                System.out.println("Total Enter is 900. Museum is closing!");
                Museum.close();
                break;
            }
            for(Map.Entry<List<String>,Integer> theticketqueue : queue.entrySet()){
                List<String> ticket = theticketqueue.getKey();
                for (String ticketid_individual : ticket){
                    //If the open turnstile is full
                    //Turnstile condition will await (waiting for signal)
                    if(current_turnstile_open == 0 || Museum.current_size == 100){
                        Turnstile_Full.await();
                    }
                    //If the open turnstile not full
                    //Visitor getting into turnstile
                    System.out.println("TicketID : " + ticketid_individual + " getting into turnstile");
                    current_turnstile_open--;
                    Thread.sleep(2000);
                    //Visitor got through the turnstile
                    current_turnstile_open++;
                    Turnstile_Full.signalAll();
                    System.out.println("TicketID : " + ticketid_individual + " got through the turnstile");
                    //Visitor enter the museum
                    Museum.current_size++;
                    Museum.total_enter++;
                    //visitor stay at museum
                    VisitorControl.stayMuseum();
                }
            }
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
