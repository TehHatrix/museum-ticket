package museum;

import java.time.LocalTime;
import java.util.Random;

public class VisitorControl {
    LocalTime time_enter;


    public VisitorControl() {
    }



    public void accessMuseum(LocalTime timeenter) throws InterruptedException {
        this.time_enter = timeenter;
        Museum.current_size++;
        Random random = new Random();
        Thread.sleep(random.nextInt(150) + 50);
        Museum.current_size--;
    }

    public void setTime_enter(LocalTime time_enter) {
        this.time_enter = time_enter;
    }

    public void buyTicket(){

    }
}
