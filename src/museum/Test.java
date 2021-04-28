package museum;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Test
{

    static class Task extends TimerTask{
        LocalDateTime currentTime;
        DateTimeFormatter format;

        public Task(LocalDateTime currentTime) {
            this.currentTime = currentTime;
            this.format = DateTimeFormatter.ofPattern("HH:mm");
        }

        @Override
        public void run() {
            if(currentTime.format(format).equals("09:00")){
                System.out.println("Museum Open : " + currentTime.format(format));
            }
            else if (currentTime.format(format).equals("18:00")){
                System.out.println("Museum Closed! : " + currentTime.format(format));
            }
            else{
                System.out.println("Current time : " + currentTime.format(format));
            }
            currentTime = currentTime.plusHours(1);
        }
    }

    public static void main(String[] args) throws ParseException {
        LocalDateTime actualDateTime = LocalDateTime.of(2018, Month.FEBRUARY, 25, 6, 0);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        Timer timer = new Timer();
        Task thetime = new Task(actualDateTime);
        timer.schedule(thetime,1000, 1000);



    }
}

