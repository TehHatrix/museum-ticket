package museum;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Test
{

    static class Task extends TimerTask{
        LocalTime currentTime;
//        DateTimeFormatter format; (To Convert String)

        public Task(LocalTime currentTime) {
            this.currentTime = currentTime;
//            this.format = DateTimeFormatter.ofPattern("HH:mm");
        }

        @Override
        public void run() {
            if(currentTime.equals(LocalTime.of(9,00))){
                System.out.println("Museum Open : " + currentTime);
            }
            else if (currentTime.equals(LocalTime.of(17,00))){
                System.out.println("Museum Closed! : " + currentTime);
            }
            else{
                System.out.println("Current time : " + currentTime);
            }
            currentTime = currentTime.plusHours(1);
        }
    }

    public static void main(String[] args) throws ParseException {
        LocalTime startingtime = LocalTime.of(8,0);
        Timer timer = new Timer();
        Task thetime = new Task(startingtime);
        timer.schedule(thetime,1000, 1000);



    }
}

