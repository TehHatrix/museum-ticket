package museum;

import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.*;

public class Test
{

    static class Task extends TimerTask{
        static LocalTime currentTime;
        Thread visitorsystem;
        Museum museum = new Museum();

        public Task(LocalTime currentTime) {
            this.currentTime = currentTime;
            this.visitorsystem = new Thread(new VisitorControl());
        }

        public static LocalTime getCurrentTime() {
            return currentTime;
        }

        @Override
        public void run() {
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
            currentTime = currentTime.plusMinutes(10);
        }
    }

    public static void main(String[] args) throws ParseException {
        Timer timer = new Timer();
        Task thetime = new Task(LocalTime.of(8,0));
        timer.schedule(thetime,1000, 1000);


    }
}

