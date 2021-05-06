package museum;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CounterTicket extends Museum implements Runnable{

    private final LocalTime counterOpen = LocalTime.of(8, 0);
    private final LocalTime counterClose = LocalTime.of(17, 0);
    private volatile AtomicInteger ticketID = new AtomicInteger(1);
    public LocalTime currentTime;

    public void buyTicket() {
        lock.lock();
        try {
            if (getClock().equals(LocalTime.of(7, 45))) {
                System.out.println("Counter is still not open. It will open in " + super.getClock().until(counterOpen, ChronoUnit.MINUTES) +
                        " Minutes");
                setBuyTime(LocalTime.of(8, 0));
            } else if (getClock().equals(counterClose)) {
                System.out.println("Counter Ticket is close.");
            } else if (getClock().equals(getBuyTime())) {
                System.out.print(getClock() + " Tickets ");
                int rangebuy = rand.nextInt(4) + 1;
                for (int i = 1; i <= rangebuy; i++) {
                    String id = String.format("T%04d", ticketID.getAndIncrement());
                    Ticket ticket = new Ticket(id, getBuyTime());
                    int timeInside = rand.nextInt(101) + 50;
                    Visitor visitor = new Visitor(ticket, timeInside);
                    getVisitorQueue().add(visitor);
                    System.out.print(visitor.getTicket().getTicketId() + " ");
                }
                System.out.print("sold\n");
                setBuyTime(getBuyTime().plusMinutes(rand.nextInt(4) + 1));
            }


        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        while(true) {

            buyTicket();
            if(getClock().equals(LocalTime.of(17, 30))) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        while(isCounterOpen()) {
//            if(getClock().equals(getBuyTime())) {
//                buyTicket();
//            }
//
//
//        }

    }
}
