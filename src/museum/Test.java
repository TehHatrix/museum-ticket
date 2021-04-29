package museum;

import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Test
{

    static class Task extends TimerTask{
        LocalTime currentTime;

        public Task(LocalTime currentTime) {
            this.currentTime = currentTime;
        }

        @Override
        public void run() {
            if(currentTime.equals(Museum.getOpentime())){
                Museum.open();
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
        VisitorControl test = new VisitorControl();
        Museum museum = new Museum();
        Timer timer = new Timer();
        Task thetime = new Task(LocalTime.of(8,0));
        timer.schedule(thetime,1000, 5000);

    }
}

