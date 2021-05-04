package museum;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class CounterTicket {

    public final LocalTime opentime = LocalTime.of(9,0);
    public final LocalTime closetime = LocalTime.of(18,0);
    private Visitor visitor;
    private Ticket ticket;
    private int ticketId = 0;
    private volatile Queue<Visitor> visitorQueue = new ConcurrentLinkedQueue<>();
    private volatile ConcurrentHashMap<String, LocalTime> museumVisitor = new ConcurrentHashMap<>();

    private int totalVisitor = 0;
    public Random rand = new Random();

    private Lock lock = new ReentrantLock();
    private Condition museumFull = lock.newCondition();
    private Condition museumEmpty = lock.newCondition();


    public Queue<Visitor> getVisitorQueue() {
        return visitorQueue;
    }

    public ConcurrentHashMap<String, LocalTime> getMuseumVisitor() {
        return museumVisitor;
    }

    public CounterTicket() {

    }

    public void addTime(LocalTime t) {
        lock.lock();
        try {
            Main.clock = Main.clock.plusMinutes(1);
        } finally {
            lock.unlock();
        }
    }

    public void visitorEnter(LocalTime currentTime) {
        lock.lock();
        try {

            int count = 0;
            while(currentTime.isAfter(opentime)) {
                if(totalVisitor == 900 || currentTime.isAfter(closetime)) {
                    System.out.println("The Museum will not accept visitor anymore!!");
                    break;
                }

                Visitor bob = visitorQueue.poll();
                LocalTime minuteInside = currentTime.plusMinutes(bob.getTimeInside());
                if(museumVisitor.size() == 100) {
                    System.out.println("Museum is at max capacity. please wait until other visitor left");
                    museumFull.await();
                }
                if(minuteInside.isAfter(closetime)) {
                    minuteInside = closetime;
                }
                museumVisitor.put(bob.getTicket().getTicketId(), minuteInside);
                totalVisitor += 1;
                count += 1;
                if(rand.nextInt(2) == 0) {
                    System.out.println(currentTime + " Ticket " + bob.getTicket().getTicketId() + " entered through Turnstile SET" +
                            count + " Staying for " + bob.getTimeInside() + " minutes. exiting at time : " + minuteInside);
                }else {
                    System.out.println(currentTime + " Ticket " + bob.getTicket().getTicketId() + " entered through Turnstile NET" +
                            count + " Staying for " + bob.getTimeInside() + " minutes. exiting at time : " + minuteInside);
                }
                System.out.println("current museumVisitor " + museumVisitor.size());

                if(visitorQueue.isEmpty()) {
                    break;
                }else if(visitorQueue.peek().getTicket().getTimeBought().equals(bob.getTicket().getTimeBought())) {
                    continue;
                }else {
                    break;
                }



            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void visitorLeft(LocalTime currentTime) {
        lock.lock();
        try{
//            if(currentTime.isAfter(closetime)) {
//
//            }
            Iterator<ConcurrentHashMap.Entry<String, LocalTime>> itr = museumVisitor.entrySet().iterator();
            while (itr.hasNext()) {
                ConcurrentHashMap.Entry<String, LocalTime> entry = itr.next();
                if(entry.getValue().equals(currentTime)) {
                    museumVisitor.remove(entry.getKey(), entry.getValue());
                    System.out.println(currentTime + " Ticket " + entry.getKey() + " exited through exit");
                    museumFull.signalAll();
                }
            }

        }finally {
            lock.unlock();
        }
    }

    public boolean buyTicket(LocalTime currentTime, int timeInside) {
        lock.lock();
//        System.out.println("from ct" + currentTime);
        try {
            if(currentTime.isBefore(opentime)) {
                System.out.println(currentTime + " Museum is still not open");
                return false;
            } else if(currentTime.isAfter(closetime)) {
                System.out.println(currentTime + "Museum is already closed");
                return false;
            } else {
                ticketId += 1;
                String id = String.format("T%04d", ticketId);
                ticket = new Ticket(id, currentTime);
                visitor = new Visitor(ticket, timeInside);
                System.out.println(currentTime + " Ticket " + visitor.toString() + " sold");
                visitorQueue.add(visitor);
                return false;
            }
        } finally {
            lock.unlock();
        }

    }
}
