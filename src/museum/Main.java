package museum;

import java.time.LocalTime;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public LocalTime clock;

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(6);
        Museum museum = new Museum();
        Museum counterTicket = new CounterTicket();
        Museum theTime = new TheTime();
        Museum se = new Entrance("South Entrance");
        Museum ne = new Entrance("North Entrance");
        Museum ee = new Exit("East Exit");
        Museum we = new Exit("West Exit");

        service.execute((Runnable) counterTicket);
        service.execute((Runnable) theTime);
        service.execute((Runnable) se);
        service.execute((Runnable) ee);
        service.execute((Runnable) ne);
        service.execute((Runnable) we);

        service.shutdown();


    }
}
