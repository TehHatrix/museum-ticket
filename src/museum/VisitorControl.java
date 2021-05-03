package museum;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

public class VisitorControl implements Runnable{
    private static String ticketid;
    public static LocalTime time_enter;
    List<String> visitor_ticket;


    public VisitorControl() {
    }


    //Buy ticket (probably ni ticket thread)
    public void buyTicket() throws InterruptedException {
        while(true){
            Random random = new Random();
            int num_of_guests = random.nextInt(5) + 1;
            int purchase_delay = random.nextInt(500) + 100;
            Thread.sleep(purchase_delay);
            //get the ticket ID / buy the ticket
            System.out.println(TicketControl.getTicketID(num_of_guests));
            List<String> visitor_ticket = TicketControl.getTicketID(num_of_guests);
            //increase running number ID
            TicketControl.increment_runningid(num_of_guests);
        }
    }

    //Enter museum through random entrances
    public static void entermuseum() throws InterruptedException {
        Random random = new Random();
        //Choose Entrance Gate
        Entrance which_gate = Museum.entrance_list.get(random.nextInt(Museum.entrance_list.size()));
        //Enter the gate
        which_gate.LetVisitorEnter();
    }

    //Stay at the museum for random time
    public static void stayMuseum() throws InterruptedException {
        Random random = new Random();
        //Stay
        int staytime = random.nextInt(150) + 50;
        Thread.sleep(staytime);
    }

    //Exit museum through random exits
    public static void exitMuseum() throws InterruptedException {
        Random random = new Random();
        //Choose Exit Gate
        Exit which_exit = Museum.exit_list.get(random.nextInt(Museum.exit_list.size()));
        //Exit the gate
        which_exit.LetVisitorExit();
    }


    @Override
    public void run() {
        try {
            entermuseum();
            exitMuseum();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
