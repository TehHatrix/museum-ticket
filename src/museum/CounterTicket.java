package museum;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CounterTicket extends Museum implements Runnable{

    private final LocalTime counterOpen = LocalTime.of(8, 0);
    private final LocalTime counterClose = LocalTime.of(17, 0);
    private volatile AtomicInteger ticketID = new AtomicInteger(1);


    public void buyTicket() {
        lock.lock();
        try {
            //check if counter is open
            if (getClock().equals(counterClose)) {
                System.out.println("Counter Ticket is close.");
                //if current time is equal to buy time
            } else if (getClock().equals(getBuyTime())) {
                System.out.print(getClock() + " Tickets ");
                //1 - 4 ticket can be sold at a time
                int rangebuy = rand.nextInt(4) + 1;
                for (int i = 1; i <= rangebuy; i++) {
                    String id = String.format("T%04d", ticketID.getAndIncrement());
                    Ticket ticket = new Ticket(id, getBuyTime());
                    int timeInside = rand.nextInt(101) + 50;
                    Visitor visitor = new Visitor(ticket, timeInside);
                    //add to visitor queue
                    getVisitorQueue().add(visitor);
                    System.out.print(visitor.getTicket().getTicketId() + " ");
                }
                System.out.print("sold\n");
                //increment next buy time from 1-4 minutes
                setBuyTime(getBuyTime().plusMinutes(rand.nextInt(4) + 1));
            }


        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        while(true) {
            // first attempt to buy will be in 7:45 and then will change to 8:00
            if (getClock().equals(LocalTime.of(7, 45))) {
                System.out.println("Counter is still not open. It will open in " + super.getClock().until(counterOpen, ChronoUnit.MINUTES) +
                        " Minutes");
                setBuyTime(LocalTime.of(8, 0));
            }
            //if counter is open
            if(isCounterOpen()) {
                buyTicket();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
