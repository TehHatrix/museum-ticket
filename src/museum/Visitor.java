package museum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Visitor {
    int ticket_id;
    LocalDateTime time_enter;
    DateTimeFormatter format;

    public Visitor(LocalDateTime time_enter) {
        this.time_enter = time_enter;
        this.format = DateTimeFormatter.ofPattern("HH:mm");
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public void access() throws InterruptedException {
        Random random = new Random();
        Thread.sleep(random.nextInt(150) + 50);



    }
}
