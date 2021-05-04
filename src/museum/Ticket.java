package museum;

import java.time.LocalTime;

public class Ticket {
    private String ticketId;
    private LocalTime timeBought;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public LocalTime getTimeBought() {
        return timeBought;
    }

    public void setTimeBought(LocalTime timeBought) {
        this.timeBought = timeBought;
    }

    public Ticket(String ticketId, LocalTime timeBought) {
        this.ticketId = ticketId;
        this.timeBought = timeBought;
    }
}
