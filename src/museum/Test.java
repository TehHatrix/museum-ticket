package museum;

import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.*;

public class Test
{

    static class Task extends TimerTask{
        LocalTime currentTime;
        Thread visitorsystem;

        public Task(LocalTime currentTime) {
            this.currentTime = currentTime;
            this.visitorsystem = new Thread(new VisitorControl());
        }

        public LocalTime getCurrentTime() {
            return currentTime;
        }

        @Override
        public void run() {
            if(currentTime.equals(TicketControl.getOpentime())){
                TicketControl.open();
                visitorsystem.start();
                System.out.println("Ticket Counter Open : " + currentTime);
            }
            else if (currentTime.equals(TicketControl.getClosetime())){
                TicketControl.close();
                System.out.println("Ticket Counter Close : " + currentTime);
            }
            if(currentTime.equals(Museum.getOpentime())){
                Museum.open();
                Museum.startEntrance();
                System.out.println("Museum open : " + currentTime);
            }
            else if (currentTime.equals(Museum.getClosetime())){
                Museum.close();
                System.out.println("Museum closed! : " + currentTime);
            }
            else{
                System.out.println("Current time : " + currentTime);
            }
            currentTime = currentTime.plusHours(1);
        }
    }

    public static void main(String[] args) throws ParseException {
        Timer timer = new Timer();
        Task thetime = new Task(LocalTime.of(8,0));
        timer.schedule(thetime,1000, 5000);


    }
}

