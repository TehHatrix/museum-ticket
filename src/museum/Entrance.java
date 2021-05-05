package museum;

import java.time.LocalTime;
import java.util.concurrent.ConcurrentLinkedQueue;

import static museum.Museum.Museum_Full;
import static museum.Museum.lock;

public class Entrance implements Runnable {
    String name;
    int num_turnstiles;
    int current_turnstile_open;
    ConcurrentLinkedQueue<Visitor> queue;


    public Entrance(String name, int num_turnstiles) {
        this.name = name;
        this.num_turnstiles = num_turnstiles;
        this.current_turnstile_open = num_turnstiles;
        this.queue = new ConcurrentLinkedQueue<Visitor>();
    }

    public void EnterQueueList(Visitor visitor) {
        queue.add(visitor);
    }

    public void LetVisitorEnter() throws InterruptedException {
        lock.lock(); //Use Semaphore for more thread
        //While Museum Open
        while (Museum.opened) {
            if (Museum.total_enter == Museum.MAX_SIZE) {
                System.out.println("Total Enter is 900. Museum will not receiving more visitor & will close!");
                Museum.close();
                break;
            }
//            while (current_turnstile_open == 0 || Museum.current_size == 100) {
//                System.out.println("Turnstile is full! Some visitors will wait in queue");
//                Turnstile_Full.await();
//            }
            if (Museum.current_size == 100) {
                System.out.println("Museum is currently full. Waiting for another visitor to left.");
                Museum_Full.await();
            }
            if (queue.peek() != null) {
                Visitor current_visitor = queue.poll();
                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_visitor + " will be entering the museum through " + current_visitor.getEntrance().name);
                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_visitor + " getting into turnstile");
                current_turnstile_open--;
//                Thread.sleep(2000);
                //Visitor got through the turnstile
                current_turnstile_open++;
//                Turnstile_Full.signal();
                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_visitor + " got through the turnstile");
                Museum.current_size++;
                Museum.total_enter++;
                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_visitor + " entered the museum successfully!");
                System.out.println("Museum Current Size : " + Museum.current_size);
                System.out.println("Museum Total Size : " + Museum.total_enter);
                //Stay for how many minutes
                int staytime = VisitorControl.stayMuseum();
                current_visitor.setStaytime(staytime);
                //Exit Museum
                LocalTime exit_Time = Test.Task.getCurrentTime().plusMinutes(current_visitor.getStaytime());
                Exit.AddConcurrentHashMap(current_visitor,exit_Time);
                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_visitor + " will be staying for " + staytime + " minutes." + " Will be exiting at " + exit_Time);
            }
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
