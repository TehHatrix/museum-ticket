package museum;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static museum.Museum.Museum_Full;

public class Exit implements Runnable {
    String name;
    int num_turnstiles;
    int current_turnstile_open;
    public static ConcurrentHashMap<Visitor, LocalTime> hmap;
    public static Lock lock2 = new ReentrantLock();
    public static Condition Turnstile_Full2 = lock2.newCondition();
    public static Condition Museum_Full2 = lock2.newCondition();


    public Exit(String name, int num_turnstiles) {
        this.name = name;
        this.num_turnstiles = num_turnstiles;
        this.current_turnstile_open = num_turnstiles;
        this.hmap = new ConcurrentHashMap<Visitor, LocalTime>();
    }


    public static void AddConcurrentHashMap(Visitor visitor, LocalTime exittime) {
        hmap.put(visitor, exittime);
    }


    public void LetVisitorExit() throws InterruptedException {
        lock2.lock();
        while (Museum.opened) {
            if (Museum.current_size == 0 && Museum.opened == false) {
                break;
            }
//            while (current_turnstile_open == 0 || Museum.current_size == 100) {
//                System.out.println("Turnstile is full! Some visitors will wait in queue");
//                Turnstile_Full.await();
            Iterator<ConcurrentHashMap.Entry<Visitor, LocalTime>> itr = hmap.entrySet().iterator();
            while (itr.hasNext()) {
                ConcurrentHashMap.Entry<Visitor, LocalTime> entry = itr.next();
                if (entry.getValue().equals(Test.Task.getCurrentTime())) {
                    //Choosing which exit
                    Random random = new Random();
                    Exit which_exit = Museum.exit_list.get(random.nextInt(Museum.exit_list.size()));
                    entry.getKey().setExit(which_exit);
                    //Exiting
                    Museum.current_size--;
                    hmap.remove(entry.getKey(), entry.getValue());
                    System.out.println(Test.Task.getCurrentTime() + " Ticket " + entry.getKey().getTicket().getTicketId() + " exited through exit " + entry.getKey().getExit().name);
                    Museum_Full2.signalAll();
                }
            }
//            if (queue.peek() != null) {
//                String current_ticket_visitor = queue.remove();
//                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_ticket_visitor + " will be exiting the museum");
//                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_ticket_visitor + " getting into turnstile");
//                current_turnstile_open--;
//                Thread.sleep(2000);
//                //Visitor got through the turnstile
//                current_turnstile_open++;
//                Turnstile_Full.signal();
//                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_ticket_visitor + " got through the turnstile");
//                System.out.println(Test.Task.getCurrentTime() + " TicketID : " + current_ticket_visitor + " exited the museum succesfully!");
//                Museum.current_size--;
//                System.out.println("Museum Current Size : " + Museum.current_size);
////                VisitorControl.stayMuseum(current_ticket_visitor);
//            }
        }
        lock2.unlock();
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
