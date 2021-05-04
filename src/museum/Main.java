package museum;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static LocalTime clock = LocalTime.of(8,0);
    public static LocalTime buyTime = LocalTime.of(8,0);
    public final static LocalTime opentime = LocalTime.of(9,0);
    public final static LocalTime closetime = LocalTime.of(18,0);
    public final static  LocalTime endTime = LocalTime.of(18, 30);
    public static Lock lock = new ReentrantLock();
//    ExecutorService service = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {

        Random rand = new Random();

        CounterTicket ct = new CounterTicket();
        Museum museum = new Museum();

//        Timer timer = new Timer();
//        TestTime theTime = new TestTime(LocalTime.of(8,0));
//        timer.schedule(theTime,1000, 500);
        Runnable theTime = () -> {
            try {
                while(true) {
                  if(clock.equals(opentime)) {
                      System.out.println("Museum Open");
                  } else if(clock.equals(closetime)) {
                      System.out.println("Museum closed");
                  } else if(clock.equals(endTime)) {
                      System.exit(1);
                  }
//                  System.out.println(clock);
//                  ct.addTime(clock);
                  if(Thread.currentThread().isAlive()) {
                      Thread.sleep(300);
                      clock = clock.plusMinutes(1);

                  }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable counterTicket = () -> {

            while(true) {
                int randomRangeBuy = rand.nextInt(4) + 1;
                boolean check = true;
                if(clock.equals(buyTime)) {
                    for(int i = 0; i < rand.nextInt(5); i++) {
                        check = ct.buyTicket(clock, (rand.nextInt(101) + 50));
                    }
                }else if(clock.equals(endTime)) {
                    break;
                }
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!check) {
//                    break;
                    buyTime = buyTime.plusMinutes(randomRangeBuy);

                }
            }
        };

        Runnable visitorEnter = () -> {
            while(true) {

                if(!ct.getVisitorQueue().isEmpty()) {
                    ct.visitorEnter(clock);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable visitorLeft = () -> {
            while(true) {
                if(!ct.getMuseumVisitor().isEmpty()) {
                    ct.visitorLeft(clock);
                }
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        };

        Thread t1 = new Thread(theTime);
        Thread t2 = new Thread(counterTicket);
        Thread t3 = new Thread(visitorEnter);
        Thread t4 = new Thread(visitorLeft);

        t1.start();
        t2.start();
        t3.start();
        t4.start();



    }




//    static class TestTime extends TimerTask {
////        LocalTime currentTime;
//
//        public TestTime(LocalTime currentTime) {
//            clock = currentTime;
//        }
//
//        @Override
//        public void run() {
//
//            clock = clock.plusMinutes(1);
//            if(clock.equals(opentime)) {
//                System.out.println("museum is open");
//            } else if(clock.equals(closetime)) {
//                System.out.println("Museum is closed");
//            } else if(clock.equals(endTime)) {
//                cancel();
////                System.exit(1);
//            }
//            System.out.println(clock);
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
////            currentTime = currentTime.plusMinutes(1);
//        }
//    }


}


