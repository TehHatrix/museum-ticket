package museum;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Entrance implements Runnable {
    String name;
    int num_turnstiles;
    int current_turnstile_open;
    Queue<String> queue;
    final Lock lock = new ReentrantLock();
    final Condition Turnstile_Full = lock.newCondition();


    public Entrance(String name, int num_turnstiles) {
        this.name = name;
        this.num_turnstiles = num_turnstiles;
        this.current_turnstile_open = num_turnstiles;
        this.queue = new LinkedList<>();
    }

    public void EnterQueueList(String ticket) {
        queue.add(ticket);
    }

    //current  bug  Exception in thread "Thread-2" java.util.NoSuchElementException (maybe beli ticket slow sangat)
    public void LetVisitorEnter() throws InterruptedException {
        lock.lock(); //Use Semaphore for more thread
        //While Museum Open
        while (Museum.opened && queue != null) {
            if (Museum.total_enter == Museum.MAX_SIZE) {
                System.out.println("Total Enter is 900. Museum will not receiving more visitor & will close!");
                Museum.close();
                break;
            }
            while (current_turnstile_open == 0 || Museum.current_size == 100) {
                System.out.println("Turnstile is full! Some visitors will wait in queue");
                Turnstile_Full.await();
            }
            if (queue.peek() != null) {
                String current_ticket_visitor = queue.remove();
                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_ticket_visitor + " will be entering the museum");
//            visitor getting to turnstile (tgh fikir camne nak bagi turnstile full)
                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_ticket_visitor + " getting into turnstile");
                current_turnstile_open--;
                Thread.sleep(2000);
                //Visitor got through the turnstile
                current_turnstile_open++;
                Turnstile_Full.signal();
                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_ticket_visitor + " got through the turnstile");
                Museum.current_size++;
                Museum.total_enter++;
                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_ticket_visitor + " entered the museum successfully!");
                System.out.println("Museum Current Size : " + Museum.current_size);
                System.out.println("Museum Total Size : " + Museum.total_enter);
                VisitorControl.stayMuseum(current_ticket_visitor);
            }


//            for(Map.Entry<List<String>,Integer> theticketqueue : queue.entrySet()){
//                List<String> ticket = theticketqueue.getKey();
//                //camne nak bagi semua masuk. instead of satu satu
//                for (String ticketid_individual : ticket){
//                    //If the open turnstile is full
//                    //Turnstile condition will await (waiting for signal)
//                    while(current_turnstile_open == 0 || Museum.current_size == 100){
//                        Turnstile_Full.await();
//                    }
//                    //If the open turnstile not full
//                    //Visitor getting into turnstile
//                    System.out.println("TicketID : " + ticketid_individual + " getting into turnstile");
//                    current_turnstile_open--;
//                    Thread.sleep(2000);
//                    //Visitor got through the turnstile
//                    current_turnstile_open++;
//                    Turnstile_Full.signal();
//                    System.out.println("TicketID : " + ticketid_individual + " got through the turnstile");
////                    queue.remove(ticketid_individual);
//                    //Visitor enter the museum
//                    Museum.current_size++;
//                    Museum.total_enter++;
//                    //visitor stay at museum
//                    VisitorControl.stayMuseum();
//                }
//            }
        }
        lock.unlock();
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
