package museum;

import java.time.LocalTime;
import java.util.Random;

public class TicketControl {
    String ticket_id;
    int current_runningid;
    LocalTime time_bought;
    final int MAX_TICKET = 9999;
    static final LocalTime opentime = LocalTime.of(8,0);
    static final LocalTime closetime = LocalTime.of(17,0);
    static boolean opened;

    public TicketControl() {
    }

    public static LocalTime getClosetime() {
        return closetime;
    }

    public static LocalTime getOpentime() {
        return opentime;
    }

    public static void open(){
        opened = true;
        System.out.println("Ticket Counter Open!");
    }

    public static void close(){
        opened = false;
        System.out.println("Ticket Counter Closed!");
    }


    public void sellTicket() throws InterruptedException {
        Random random = new Random();
        Thread.sleep(random.nextInt(400) + 100);
    }




}
