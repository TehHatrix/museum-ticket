package museum;

import java.time.LocalTime;

public class TheTime extends Museum implements Runnable{


    @Override
    public void run() {

        try {
            while(true) {
                if(getClock().equals(opentime)) {
                    System.out.println("Museum is open");
                    setMuseumOpen(true);
                } else if(getClock().equals(closetime)) {
                    System.out.println("Museum is close");
                    setMuseumOpen(false);
                }
                if(getClock().equals(LocalTime.of(8, 0))) {
                    System.out.println("Counter Ticket is open");
                    setCounterOpen(true);
                }else if(getClock().equals(LocalTime.of(17, 0))) {
                    System.out.println("Counter Ticket is close");
                    setCounterOpen(false);
                }
                if(getClock().equals(LocalTime.of(18, 30))) {
//                    break;
                    System.exit(1);
                }
//                System.out.println("Current Time " + getClock());
//                System.out.println("size museum : " + getMuseumVisitor().size());
                if(Thread.currentThread().isAlive()) {
                    Thread.sleep(300);
                    setClock(getClock().plusMinutes(1));
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
