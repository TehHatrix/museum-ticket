package museum;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.*;

public class Test
{

    static class Task extends TimerTask{
        static LocalTime currentTime;
        Thread visitorsystem;
        Museum museum = new Museum();
        public static LocalTime PurchaseTime;
        public static boolean ongoing_purchase;

        public Task(LocalTime currentTime) {
            this.currentTime = currentTime;
            this.visitorsystem = new Thread(new VisitorControl());
            this.PurchaseTime = TicketControl.getOpentime();
            ongoing_purchase = false;
        }

        public static LocalTime getCurrentTime() {
            return currentTime;
        }

        public static LocalTime getPurchaseTime() {
            return PurchaseTime;
        }

        @Override
        public void run() {
            Random random = new Random();
            int purchase_delay = random.nextInt(4) + 1;
            if(currentTime.equals(TicketControl.getOpentime())){
                TicketControl.open();
                visitorsystem.start();
                System.out.println(currentTime + " Ticket Counter Open!" );
            }
            else if (currentTime.equals(TicketControl.getClosetime())){
                TicketControl.close();
                System.out.println(currentTime + " Ticket Counter Closed!" );
            }
            if(currentTime.equals(Museum.getOpentime())){
                museum.open();
                try {
                    museum.startEntranceExits();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(currentTime + " Museum Open!" );
            }
            else if (currentTime.equals(Museum.getClosetime())){
                museum.close();
                System.out.println(currentTime + " Museum Closed!");
            }
            else{
                System.out.println("Current time : " + currentTime);
            }
            if(!ongoing_purchase){
                PurchaseTime = currentTime.plusMinutes(purchase_delay);
            }

            currentTime = currentTime.plusMinutes(1);
        }
    }

    public static void main(String[] args) throws ParseException {
        Timer timer = new Timer();
        Task thetime = new Task(LocalTime.of(8,0));
        timer.schedule(thetime,0, 0);


    }
}

