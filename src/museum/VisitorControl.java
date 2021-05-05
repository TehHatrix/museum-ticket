package museum;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
public class VisitorControl implements Runnable {
    public static CopyOnWriteArrayList<Ticket> visitor_ticket;

    public VisitorControl() {
        this.visitor_ticket = new CopyOnWriteArrayList<Ticket>();
    }


    public static void buyTicketandOverallFlow() throws InterruptedException {
        while (TicketControl.opened) {
            Random random = new Random();
            int num_of_guests = random.nextInt(4) + 1;
            if (Test.Task.getCurrentTime().equals(Test.Task.getPurchaseTime())) {
                //runbanyaksangat
                // if (currenttime already run the method) *executed (test yang decide)
                // = dont run the method
                if(!Test.Task.executed){
                    Test.Task.executed = true;
                    //get the ticket ID / buy the ticket
                    visitor_ticket = TicketControl.getTicketID(num_of_guests);
                    //means that it is closed so the thread will die here.
                    if (visitor_ticket == null) {
                        break;
                    }
                    String ticket_sold = "";
                    for (int i = 0; i < visitor_ticket.size(); i++) {
                        ticket_sold += visitor_ticket.get(i).getTicketId() + " ";
                    }
                    System.out.println(Test.Task.getCurrentTime() + " " + ticket_sold + " sold!");
                    for (Ticket individual_ticket : visitor_ticket) {
                        Visitor visitor = new Visitor(individual_ticket);
                        entermuseum(visitor);
                    }
                }
            }
        }
    }

    //Enter museum through random entrances
    public static void entermuseum(Visitor visitor) throws InterruptedException {
        Random random = new Random();
        //Choose Entrance Gate
        //current bug it chose only one gate
        Entrance which_gate = Museum.entrance_list.get(random.nextInt(Museum.entrance_list.size()));
        //Enter the gate
        visitor.setEntrance(which_gate);
        which_gate.EnterQueueList(visitor);
    }

    //Stay at the museum for random time
    public static int stayMuseum() throws InterruptedException {
        Random random = new Random();
        int staytime = random.nextInt(101) + 50;
        return staytime;
    }

    //Exit museum through random exits
    public static void exitMuseum(Visitor visitor) throws InterruptedException {
        Random random = new Random();
        //Choose Exit Gate
        Exit which_exit = Museum.exit_list.get(random.nextInt(Museum.exit_list.size()));
        //Exit the gate
//        which_exit.EnterQueueList(visitor_ticket);
    }


    public void run() {
        try {
            buyTicketandOverallFlow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
