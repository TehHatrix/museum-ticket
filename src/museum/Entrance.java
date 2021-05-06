package museum;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Entrance extends Museum implements Runnable{

    private String name;
    private Visitor bob;
    private LocalTime current;

    public Entrance(String name) {
        this.name = name;
        current = getClock();

    }

    public void letVisitorEnter() {
        lock.lock();
        try {
//            if(getMuseumVisitor().size() == 100) {
//                System.out.println("museum is full. waiting for visitor to left");
//                museumFull.await();
//            }
            int countT = rand.nextInt(5) + 1;
            int turnstile = 1;
//            while(true) {
//
//                if(turnstile == countT || getVisitorQueue().isEmpty()) {
//                    break;
//                }
                if (getVisitorQueue().peek() != null) {
                    bob = getVisitorQueue().poll();
                    LocalTime timeExit = getClock().plusMinutes(bob.getTimeInside());
                    int bobTime = bob.getTimeInside();
                    if(timeExit.isAfter(closetime)) {
                        bobTime = (int) getClock().until(closetime, ChronoUnit.MINUTES);
                        timeExit = closetime;
                    }
                    addHashMap(bob.getTicket().getTicketId(), timeExit);
                    getTotalVisitor().incrementAndGet();
                    System.out.println(getClock() + " Tickets " + bob.getTicket().getTicketId() +
                            " Entered through " + this.name + " Turnstile " + turnstile +
                            " staying for " + bobTime + " minutes. exiting at : " + timeExit);
                }
//                turnstile += 1;
//            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        int c = 0;
        while(true) {
//            if(!current.equals(getClock()) && c == 1) {
//                continue;
//            }
            if(getTotalVisitor().get() == 900) {
                System.out.println("The museum total visitor is 900. will not entertain any more visitor");
                setMuseumOpen(false);
                break;
            }
            if(isMuseumOpen()) {
                letVisitorEnter();
                c += 1;
            }


            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
