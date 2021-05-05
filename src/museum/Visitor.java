package museum;

public class Visitor {

    private Ticket ticket;
    private int staytime;
    private Entrance entrance;
    private Exit exit;

    public Visitor(Ticket ticket) {
        this.ticket = ticket;
    }

    public Exit getExit() {
        return exit;
    }

    public Entrance getEntrance() {
        return entrance;
    }

    public void setExit(Exit exit) {
        this.exit = exit;
    }

    public void setEntrance(Entrance entrance) {
        this.entrance = entrance;
    }

    public void setStaytime(int timeinside) {
        this.staytime = timeinside;
    }

    public int getStaytime() {
        return staytime;
    }

    public Ticket getTicket() {
        return ticket;
    }

    @Override
    public String toString() {
        return ticket.getTicketId();
    }
}
