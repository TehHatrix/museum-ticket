package museum;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Museum {

    private Random rand = new Random();
    private Visitor visitor;
    public final LocalTime opentime = LocalTime.of(9,0);
    public final LocalTime closetime = LocalTime.of(18,0);

//    private volatile LinkedList<Visitor> museumVisitor = new LinkedList<>();
//    private Queue<Visitor> visitorQueue = new ConcurrentLinkedQueue<>();
    private volatile LinkedHashMap<Visitor, LocalTime> museumVisitor = new LinkedHashMap<>();
    private volatile int totalVisitor = 0;

    private Lock lock = new ReentrantLock();
    private Condition museumFull = lock.newCondition();
    private Condition ticketSoldOut = lock.newCondition();
    private CounterTicket ct = new CounterTicket();

    private Entrance south = new Entrance("South Entrance");
    private Entrance north = new Entrance("North Entrance");


    public void visitorEnter(Queue<Visitor> visitorQueue) {
        lock.lock();
        try {
                int enterAtTime = rand.nextInt(4) + 1;
                if(totalVisitor == 900 || Main.clock.isAfter(closetime)) {
                    System.out.println("The Museum will not accept visitor anymore!!");
                }
                LocalTime checkTime;
                while(true) {
                    int count = 0;

                    visitor = visitorQueue.poll();
                    System.out.println(visitor.toString() +" lksjdflk;jasdf");
                    int enterGate = rand.nextInt(2);
//                    LocalTime timeInside = visitor.getTicket().getTimeBought().plusMinutes(visitor.getTimeInside());
                    if(museumVisitor.size() == 100) {
                        museumFull.await();
                    }
                    museumVisitor.put(visitor, LocalTime.of(9,9));

                        count += 1;
                        System.out.println(Main.clock + " Ticket " + visitor.getTicket().getTicketId() + " entered through SET"
                                + count + ". Staying for " + visitor.getTimeInside() + " minutes");

//                    } else {
//                        count += 1;
//                        System.out.println(Main.clock + " Ticket " + visitor.getTicket().getTicketId() + " entered through NET"
//                                + count + ". Staying for " + visitor.getTimeInside() + " minutes");
//                    }
                    break;


//                    if(visitor.getTicket().getTimeBought().equals(ct.getVisitorQueue().peek().getTicket().getTimeBought()) && !ct.getVisitorQueue().isEmpty()) {
//                        continue;
//                    }else {
//                        break;
//                    }


                }




        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


//    public void generateVisitor(String currentTime) {
//
//        ticket = new Ticket(, currentTime);
//        while(true) {
//            if(rand.nextInt(2) == 1 ) {
//                id += 1;
//                String ticketID = String.format("T%03d", id);
//                ticket = new Ticket(ticketID, 800);
//                visitor = new Visitor(ticket);
//                visitorQueue.add(visitor);
//            }
//
//            if(id == 10) {
//                break;
//            }
//        }
//    }




}



