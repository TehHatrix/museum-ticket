package museum;

import java.time.LocalTime;

public class TheTime extends Museum implements Runnable{


    @Override
    public void run() {

        try {
            while(true) {
                //set museum open when current time is same with open time and vice versa
                if(getClock().equals(opentime)) {
                    System.out.println("Museum is open");
                    setMuseumOpen(true);
                } else if(getClock().equals(closetime)) {
                    System.out.println("Museum is close");
                    setMuseumOpen(false);
                }
                //set counter open when 8:00 and close at 17:00
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
                    //time will move 1 minutes after 300milisecond
                    setClock(getClock().plusMinutes(1));
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
