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
    Queue<String> queue; //not thread safe
    final Lock lock = new ReentrantLock();
    final Condition Turnstile_Full = lock.newCondition();


    public Exit(String name, int num_turnstiles) {
        this.name = name;
        this.num_turnstiles = num_turnstiles;
        this.current_turnstile_open = num_turnstiles;
        this.queue = new LinkedList<>();
    }

    public void EnterQueueList(String ticket) {
        queue.add(ticket);
    }


    public void LetVisitorExit() throws InterruptedException {
        lock.lock();
        //If the open turnstile is full
        //Turnstile condition will await (waiting for signal)
        while(true){
            if(Museum.current_size == 0 && Museum.opened == false){
                break;
            }
            while (current_turnstile_open == 0 || Museum.current_size == 100) {
                System.out.println("Turnstile is full! Some visitors will wait in queue");
                Turnstile_Full.await();
            }
            if (queue.peek() != null) {
                String current_ticket_visitor = queue.remove();
//            visitor getting to turnstile
                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_ticket_visitor + " will be exiting the museum");
                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_ticket_visitor + " getting into turnstile");
                current_turnstile_open--;
                Thread.sleep(2000);
                //Visitor got through the turnstile
                current_turnstile_open++;
                Turnstile_Full.signal();
                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_ticket_visitor + " got through the turnstile");
                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_ticket_visitor + " exited the museum succesfully!");
                Museum.current_size--;
                System.out.println("Museum Current Size : " + Museum.current_size);
                VisitorControl.stayMuseum(current_ticket_visitor);
            }
        }
        lock.unlock();
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
